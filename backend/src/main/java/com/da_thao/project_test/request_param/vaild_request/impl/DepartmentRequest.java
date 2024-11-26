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
    public boolean checkRequest(Department requestData) {
        Map<String, String> detailError = new HashMap<>();

        if (requestData == null)
            detailError.put("department ", "department object is null!");

        assert requestData != null;
        if (requestData.getName() == null || requestData.getName().isEmpty())
            detailError.put("name", "Department name  is null or empty!");

        if (!detailError.isEmpty()) {
            throw new ApiException(ErrorCode.BAD_REQUEST, detailError);
        }

        return true;
    }
}


