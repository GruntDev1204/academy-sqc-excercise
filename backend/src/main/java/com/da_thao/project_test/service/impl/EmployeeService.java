package com.da_thao.project_test.service.impl;

import com.da_thao.project_test.dto.code_response.impl.ErrorCode;
import com.da_thao.project_test.exception.ApiException;
import com.da_thao.project_test.model.Employee;
import com.da_thao.project_test.dto.data_filter.FindEmployee;
import com.da_thao.project_test.repository.InterfaceRepository;
import com.da_thao.project_test.request_param.vaild_request.RequestInterface;
import com.da_thao.project_test.service.InterfaceService;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.List;

@Primary
@Service
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class EmployeeService implements InterfaceService<Employee, FindEmployee> {

    InterfaceRepository<Employee, FindEmployee> employeeRepository;
    RequestInterface<Employee> requestDataEmployees;

    @Override
    public List<Employee> getAll(FindEmployee requestParam) {
        return this.employeeRepository.getAll(requestParam);
    }

    @Override
    public Employee findById(Integer id) {
        return this.employeeRepository.findById(id);
    }

    @Override
    public Employee create(Employee e) {
        if (requestDataEmployees.checkRequest(e)) {
            return this.employeeRepository.create(e);
        }
        return null;
    }

    @Override
    public Boolean delete(Integer id) {
        return this.employeeRepository.delete(id);
    }

    @Override
    public Employee update(Integer id, Employee e) {
        if (requestDataEmployees.checkRequest(e)) {
            return this.employeeRepository.update(id, e);
        }

        return null;
    }

}
