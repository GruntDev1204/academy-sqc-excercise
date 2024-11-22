package com.da_thao.project_test.repository.impl;

import com.da_thao.project_test.exception.ApiException;
import com.da_thao.project_test.dto.code_response.impl.ErrorCode;
import com.da_thao.project_test.model.Employee;
import com.da_thao.project_test.config.DatabaseConnection;
import com.da_thao.project_test.dto.data_filter.FindEmployee;
import com.da_thao.project_test.repository.InterfaceRepository;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.*;

@Primary
@Repository
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@AllArgsConstructor
public class EmployeeRepository implements InterfaceRepository<Employee, FindEmployee> {

    private Employee getBuild(ResultSet resultSet) throws SQLException {
        return Employee.builder()
                .id(resultSet.getInt("id"))
                .salary(resultSet.getDouble("salary"))
                .fullName(resultSet.getString("full_name"))
                .gender(resultSet.getBoolean("gender") ? "Male" : "Female")
                .birthDay(resultSet.getDate("birthDay").toLocalDate())
                .phoneNumber(resultSet.getString("phone_number"))
                .departmentId(resultSet.getInt("department_id"))
                .avatar(resultSet.getString("avatar"))
                .build();
    }

    @Override
    public List<Employee> getAll(FindEmployee requestParam) {
        List<Employee> employees = new ArrayList<>();
        String query = "SELECT * FROM employees";

        try (ResultSet resultSet = DatabaseConnection.executeQuery(query)) {
            while (resultSet.next()) {
                Employee employee = getBuild(resultSet);
                employees.add(employee);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return employees;
    }

    @Override
    public Employee findById(Integer id) {
        Employee employee = null;
        String query = "SELECT * FROM employees WHERE id = ?";

        try (ResultSet resultSet = DatabaseConnection.executeQuery(query, id)) {
            while (resultSet.next()) {
                employee = getBuild(resultSet);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        if (employee == null) throw new ApiException(ErrorCode.GENERAL_NOT_FOUND);

        return employee;
    }

    @Override
    public Employee create(Employee e) {
        String query = "INSERT INTO employees ( full_name, salary, gender, phone_number, birthDay, avatar, department_id) " +
                "VALUES ( ?, ?, ?, ?, ?, ?, ?)";
        Integer generatedId = DatabaseConnection.executeUpdate(query, e.getFullName(), e.getSalary(),
                e.getGender().equalsIgnoreCase("Male"), e.getPhoneNumber(),
                java.sql.Date.valueOf(e.getBirthDay()), e.getAvatar(), e.getDepartmentId());

        if (generatedId > 0) {
            return this.findById(generatedId);
        } else {
            throw new ApiException(ErrorCode.CREATE_FAILED);
        }
    }

    @Override
    public Boolean delete(Integer id) {
        Employee employee = this.findById(id);
        if (employee == null) return false;

        String query = "DELETE FROM employees " +
                "WHERE id = ?";
        DatabaseConnection.executeUpdate(query, id);

        return true;
    }

    @Override
    public Employee update(Integer id, Employee updatedEmployee) {
        Employee e = this.findById(id);
        if (e == null) return null;

        e.setFullName(updatedEmployee.getFullName());
        e.setBirthDay(updatedEmployee.getBirthDay());
        e.setGender(updatedEmployee.getGender());
        e.setSalary(updatedEmployee.getSalary());
        e.setPhoneNumber(updatedEmployee.getPhoneNumber());
        e.setDepartmentId(updatedEmployee.getDepartmentId());
        e.setAvatar(updatedEmployee.getAvatar());

        String query = "UPDATE employees SET full_name = ?, salary = ?, gender = ?, phone_number = ?, " +
                "birthDay = ?, avatar = ?, department_id = ? WHERE id = ?";

        DatabaseConnection.executeUpdate(query, updatedEmployee.getFullName(), updatedEmployee.getSalary(),
                updatedEmployee.getGender().equalsIgnoreCase("Male"), updatedEmployee.getPhoneNumber(),
                java.sql.Date.valueOf(updatedEmployee.getBirthDay()), updatedEmployee.getAvatar(),
                updatedEmployee.getDepartmentId(), id);

        return this.findById(id);
    }

}
