package com.da_thao.project_test.request_param.vaild_request.impl;

import com.da_thao.project_test.exception.ApiException;
import com.da_thao.project_test.dto.code_response.impl.ErrorCode;
import com.da_thao.project_test.model.Department;
import com.da_thao.project_test.request_param.vaild_request.InterfaceRequest;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
@Primary
public class DepartmentRequest implements InterfaceRequest<Department> {
    @Override
    public boolean checkRequest(Department departmentRequestData) {
        Map<String, String> detailError = new HashMap<>();

        if (departmentRequestData == null)
            detailError.put("departmentRequestData", "departmentRequestData object is null!");

        assert departmentRequestData != null;
        if (departmentRequestData.getName() == null || departmentRequestData.getName().isEmpty())
            detailError.put("name", "Department name  is null or empty!");

        if (!detailError.isEmpty()) {
            throw new ApiException(ErrorCode.BAD_REQUEST, detailError);
        }

        return true;
    }
}


