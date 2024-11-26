package com.da_thao.project_test.service.impl;

import com.da_thao.project_test.dto.code_response.impl.ErrorCode;
import com.da_thao.project_test.exception.ApiException;
import com.da_thao.project_test.model.Employee;
import com.da_thao.project_test.dto.filter_information.FindEmployee;
import com.da_thao.project_test.repository.InterfaceRepository;
import com.da_thao.project_test.request_param.vaild_request.InterfaceRequest;
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
    InterfaceRequest<Employee> requestDataEmployees;

    @Override
    public List<Employee> getAll(FindEmployee requestParam) {
        return this.employeeRepository.getAll(requestParam);
    }

    @Override
    public Employee findById(Integer id) {
        return this.employeeRepository.findById(Long.valueOf(id));
    }

    @Override
    public Employee create(Employee data) {
        if (requestDataEmployees.checkRequest(data))
            return this.employeeRepository.create(data);
        else throw new ApiException(ErrorCode.CREATE_FAILED);
    }

    @Override
    public Boolean delete(Integer id) {
        return this.employeeRepository.delete(Long.valueOf(id));
    }

    @Override
    public Employee update(Integer id, Employee newData) {
        if (requestDataEmployees.checkRequest(newData))
            return this.employeeRepository.update(Long.valueOf(id), newData);
        else throw new ApiException(ErrorCode.UPDATE_FAILED);
    }

}
