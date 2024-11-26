package com.da_thao.project_test.repository.impl;

import com.da_thao.project_test.config.HibernateConnection;
import com.da_thao.project_test.dto.code_response.impl.ErrorCode;
import com.da_thao.project_test.exception.ApiException;
import com.da_thao.project_test.model.Employee;
import com.da_thao.project_test.dto.filter_information.FindEmployee;
import com.da_thao.project_test.repository.InterfaceRepository;
import com.da_thao.project_test.request_param.filter_data.InterfaceFilterData;
import com.da_thao.project_test.request_param.filter_data.impl.QueryFilterData;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

import java.util.*;

@Primary
@Repository
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@AllArgsConstructor
public class EmployeeRepository implements InterfaceRepository<Employee, FindEmployee> {
    HibernateConnection<Employee> connection;
    InterfaceFilterData<FindEmployee> employeeFilter;

    @Override
    public List<Employee> getAll(FindEmployee requestParam) {
        QueryFilterData queryData = employeeFilter.getFilterData(requestParam);
        String hql = "FROM Employee " + queryData.hql();

        List<Employee> es = connection.getQuery(queryData, hql, Employee.class);
        if(es == null) throw new ApiException(ErrorCode.GENERAL_GET_FAILED);

        return es;
    }

    @Override
    public Employee findById(Long id) {
        Employee employee = connection.getQuery(id , Employee.class);
        if (employee == null) throw new ApiException(ErrorCode.GENERAL_NOT_FOUND);

        return employee;
    }

    @Override
    public Employee create(Employee data) {
        String defaultAvatar = "https://firebasestorage.googleapis.com/v0/b/trung1204-bdc27.appspot.com/o/sqc-academy%2F6596121.png?alt=media&token=866d2e85-7699-4d1c-9f45-aa60b172969c";
        if (data.getAvatar() == null || data.getAvatar().isEmpty())  data.setAvatar(defaultAvatar);
        connection.useTask("create" , data);

        return data;
    }

    @Override
    public Boolean delete(Long id) {
        Employee employee = this.findById(id);
        connection.useTask("delete" , employee);

        return true;
    }

    @Override
    public Employee update(Long id, Employee newData) {
        Employee employee = this.findById(id);

        employee.setFullName(newData.getFullName());
        employee.setBirthDay(newData.getBirthDay());
        employee.setGender(newData.getGender());
        employee.setSalary(newData.getSalary());
        employee.setPhoneNumber(newData.getPhoneNumber());
        employee.setAvatar(newData.getAvatar());
        employee.setDepartment(newData.getDepartment());

        connection.useTask("update" , employee);

        return employee;
    }

}
