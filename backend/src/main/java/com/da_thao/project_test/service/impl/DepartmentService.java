package com.da_thao.project_test.service.impl;

import com.da_thao.project_test.dto.code_response.impl.ErrorCode;
import com.da_thao.project_test.exception.ApiException;
import com.da_thao.project_test.model.Department;
import com.da_thao.project_test.repository.impl.DepartmentRepository;
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
public class DepartmentService implements InterfaceService<Department, Object> {
    DepartmentRepository dR;
    InterfaceRequest<Department> requestDepartment;

    @Override
    public List<Department> getAll(Object requestParam) {
        return dR.getAll(requestParam);
    }

    @Override
    public Department findById(Integer id) {
        return dR.findById(Long.valueOf(id));
    }

    @Override
    public Department create(Department data) {
        if (requestDepartment.checkRequest(data)) return dR.create(data);
        else throw new ApiException(ErrorCode.GENERAL_GET_FAILED);
    }

    @Override
    public Boolean delete(Integer id) {
        return dR.delete(Long.valueOf(id));
    }

    @Override
    public Department update(Integer id, Department newData) {
        if (requestDepartment.checkRequest(newData)) return dR.update(Long.valueOf(id), newData);
        else throw new ApiException(ErrorCode.UPDATE_FAILED);
    }
}
