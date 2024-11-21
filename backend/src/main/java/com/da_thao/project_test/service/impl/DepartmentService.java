package com.da_thao.project_test.service.impl;

import com.da_thao.project_test.dto.code_response.impl.ErrorCode;
import com.da_thao.project_test.exception.ApiException;
import com.da_thao.project_test.model.Department;
import com.da_thao.project_test.repository.impl.DepartmentRepository;
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
public class DepartmentService implements InterfaceService<Department, Object> {
    DepartmentRepository dR;
    RequestInterface<Department> requestDepartment;

    @Override
    public List<Department> getAll(Object requestParam) {
        return dR.getAll(requestParam);
    }

    @Override
    public Department findById(Integer id) {
        return dR.findById(id);
    }

    @Override
    public Department create(Department d) {
        if (!requestDepartment.checkRequest(d)) throw new ApiException(ErrorCode.DEPARTMENT_GET_ALL_FAILED);
        return dR.create(d);
    }

    @Override
    public Boolean delete(Integer id) {
        return dR.delete(id);
    }

    @Override
    public Department update(Integer id, Department d) {
        if (requestDepartment.checkRequest(d)) throw new ApiException(ErrorCode.EMPLOYEES_CREATE_FAILED);
        return dR.update(id, d);
    }
}
