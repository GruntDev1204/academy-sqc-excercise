package com.da_thao.project_test.repository.impl;

import com.da_thao.project_test.config.DatabaseConnection;
import com.da_thao.project_test.exception.ApiException;
import com.da_thao.project_test.dto.code_response.impl.ErrorCode;
import com.da_thao.project_test.model.Department;
import com.da_thao.project_test.repository.InterfaceRepository;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


@Primary
@Repository
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class DepartmentRepository implements InterfaceRepository<Department, Object> {

    private static Department getBuild(ResultSet resultSet) throws SQLException {
        return Department.builder()
                .id(resultSet.getInt("id"))
                .name(resultSet.getString("name"))
                .build();
    }

    @Override
    public List<Department> getAll(Object requestParam) {
        List<Department> departments = new ArrayList<>();
        String query = "SELECT * FROM departments";

        try (ResultSet resultSet = DatabaseConnection.executeQuery(query)) {
            while (resultSet.next()) {
                Department d = getBuild(resultSet);
                departments.add(d);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return departments;
    }

    @Override
    public Department findById(Integer id) {

        Department department = null;
        String query = "SELECT * FROM departments WHERE id = ?";

        try (ResultSet resultSet = DatabaseConnection.executeQuery(query, id)) {
            if (resultSet.next()) {
                department = getBuild(resultSet);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if (department == null) throw new ApiException(ErrorCode.DEPARTMENT_NOT_FOUND);
        return department;

    }

    @Override
    public Department create(Department department) {
        String query = "INSERT INTO departments (name) VALUES (?) ";

        Integer rowsAffected = DatabaseConnection.executeUpdate(query, department.getName());

        if (rowsAffected > 0) {
            return findById(rowsAffected);
        } else throw new ApiException(ErrorCode.EMPLOYEES_CREATE_FAILED);
    }

    @Override
    public Boolean delete(Integer id) {
        Department d = this.findById(id);
        if (d == null) return false;

        String query = "DELETE FROM departments " +
                "WHERE id = ?";
        int rowsAffected = DatabaseConnection.executeUpdate(query, id);
        return true;
    }

    @Override
    public Department update(Integer id, Department department) {
        Department updateDepartment = this.findById(id);

        updateDepartment.setName(department.getName());

        String query = "UPDATE departments SET name = ? WHERE id = ?";
        int rowsAffected = DatabaseConnection.executeUpdate(query, department.getName(), id);
        if (rowsAffected > 0) return updateDepartment;
        else throw new ApiException(ErrorCode.EMPLOYEES_UPDATE_FAILED);
    }

}
