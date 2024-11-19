package com.da_thao.project_test.Request_param.vaild_request.impl;

import com.da_thao.project_test.Exception.ApiException;
import com.da_thao.project_test.DTO.code_response.impl.ErrorCode;
import com.da_thao.project_test.Model.Department;
import com.da_thao.project_test.Request_param.vaild_request.RequestInterface;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
@Primary
public class DepartmentRequest implements RequestInterface<Department> {
    @Override
    public boolean checkRequest(Department departmentRequestData) {
        Map<String, String> detailError = new HashMap<>();

        if (departmentRequestData == null) detailError.put("departmentRequestData", "departmentRequestData object is null!");

        assert departmentRequestData!= null;
        if( departmentRequestData.getName() == null || departmentRequestData.getName().isEmpty()) detailError.put("name", "Department name  is null or empty!");

        if (!detailError.isEmpty()) {
            throw new ApiException(ErrorCode.CREATE_FAILED, detailError);
        }

        return true;
    }
}
