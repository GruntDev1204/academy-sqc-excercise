package com.grunt_dev.project_main.service.impl;

import com.grunt_dev.project_main.attribute.attribute_class.FindEmployee;
import com.grunt_dev.project_main.attribute.attribute_data.InterfaceAttributeData;
import com.grunt_dev.project_main.dto.code_status.impl.ErrorCode;
import com.grunt_dev.project_main.dto.exception.ApiException;
import com.grunt_dev.project_main.model.Employee;
import com.grunt_dev.project_main.repository.IEmployeeRepository;
import com.grunt_dev.project_main.request.InterfaceRequest;
import com.grunt_dev.project_main.service.InterfaceService;
import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;

@Primary
@Service
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class EmployeeService implements InterfaceService<Employee, FindEmployee, List<Employee>> {
    IEmployeeRepository employeeRepository;
    InterfaceRequest<Employee> requestDataEmployees;
    InterfaceAttributeData<FindEmployee, Employee> attr;

    @Override
    public List<Employee> getAll(FindEmployee requestParam, Integer index, Integer size) {
        Specification<Employee> attribute = attr.getAttribute(requestParam);
        return employeeRepository.findAll(attribute);
    }

    @Override
    public Employee findById(Long id) {
        return employeeRepository.findById(id).orElseThrow(() -> new ApiException(ErrorCode.GENERAL_NOT_FOUND));
    }

    @Override
    @Transactional
    public Employee create(Employee data) {
        if (requestDataEmployees.checkRequest(data)) {
            String dfAvatar = "https://firebasestorage.googleapis.com/v0/b/trung1204-bdc27.appspot.com/o/sqc-academy%2Fdef.jpg?alt=media&token=c081698f-b51f-4016-9862-6fdce7ce10e9";
            String avatar = (data.getAvatar() == null || data.getAvatar().isEmpty()) ? dfAvatar : data.getAvatar();
            data.setAvatar(avatar);
            return employeeRepository.save(data);
        } else {
            throw new ApiException(ErrorCode.BAD_REQUEST);
        }
    }

    @Override
    public Boolean delete(Long id) {
        this.findById(id);
        employeeRepository.deleteById(id);
        return true;
    }

    @Override
    public Employee update(Long id, Employee newData) {
        this.findById(id);
        if (requestDataEmployees.checkRequest(newData)) {
            newData.setId(id);
            return employeeRepository.save(newData);
        } else {
            throw new ApiException(ErrorCode.BAD_REQUEST);
        }
    }
}
