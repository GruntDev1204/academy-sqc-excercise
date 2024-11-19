package com.da_thao.project_test.Service.impl;

import com.da_thao.project_test.Model.Employee;
import com.da_thao.project_test.DTO.data_filter.FindEmployee;
import com.da_thao.project_test.Resposritory.InterfaceRepository;
import com.da_thao.project_test.Service.InterfaceService;
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
        return this.employeeRepository.create(e);
    }

    @Override
    public Boolean delete(Integer id) {
        return this.employeeRepository.delete(id);
    }

    @Override
    public Employee update(Integer id, Employee e) {
        return this.employeeRepository.update(id, e);
    }

}
