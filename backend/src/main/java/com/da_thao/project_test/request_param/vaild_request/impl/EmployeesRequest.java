package com.da_thao.project_test.request_param.vaild_request.impl;

import com.da_thao.project_test.exception.ApiException;
import com.da_thao.project_test.dto.code_response.impl.ErrorCode;
import com.da_thao.project_test.model.Employee;
import com.da_thao.project_test.request_param.vaild_request.InterfaceRequest;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
@Primary
public class EmployeesRequest implements InterfaceRequest<Employee> {

    @Override
    public boolean checkRequest(Employee requestData) {
        Map<String, String> detailError = new HashMap<>();

        if (requestData == null) detailError.put("employeeRequestData", "employeeRequestData object is null");

        assert requestData != null;
        if (requestData.getFullName() == null || requestData.getFullName().isEmpty())
            detailError.put("fullName", "Full name cannot be empty!");

        if (requestData.getBirthDay() == null)
            detailError.put("birthDay", "Birth date cannot be null or empty!");

        if (requestData.getGender() == null || requestData.getGender().isEmpty())
            detailError.put("gender", "Gender cannot null or empty!");
        else if (!requestData.getGender().equals("Male") && !requestData.getGender().equals("Female"))
            detailError.put("gender", "Gender must be Male or Female");

        if (requestData.getSalary() <= 0) detailError.put("salary", "Salary must be greater than zero");

        if (requestData.getPhoneNumber() == null || requestData.getPhoneNumber().isEmpty())
            detailError.put("phoneNumber", "Phone number cannot be empty");
        else if (requestData.getPhoneNumber().length() != 10)
            detailError.put("phoneNumber", "Phone number must be 10 characters");

        if (!detailError.isEmpty()) throw new ApiException(ErrorCode.BAD_REQUEST, detailError);

        return true;
    }

}
