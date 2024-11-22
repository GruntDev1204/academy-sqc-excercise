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
    public boolean checkRequest(Employee employeeRequestData) {
        Map<String, String> detailError = new HashMap<>();

        if (employeeRequestData == null) detailError.put("employeeRequestData", "employeeRequestData object is null");

        assert employeeRequestData != null;
        if (employeeRequestData.getFullName() == null || employeeRequestData.getFullName().isEmpty())
            detailError.put("fullName", "Full name cannot be empty!");

        if (employeeRequestData.getBirthDay() == null)
            detailError.put("birthDay", "Birth date cannot be null or empty!");

        if (employeeRequestData.getGender() == null || employeeRequestData.getGender().isEmpty())
            detailError.put("gender", "Gender cannot null or empty!");
        else if (!employeeRequestData.getGender().equals("Male") && !employeeRequestData.getGender().equals("Female"))
            detailError.put("gender", "Gender must be Male or Female");

        if (employeeRequestData.getSalary() <= 0) detailError.put("salary", "Salary must be greater than zero");

        if (employeeRequestData.getPhoneNumber() == null || employeeRequestData.getPhoneNumber().isEmpty())
            detailError.put("phoneNumber", "Phone number cannot be empty");

        if (employeeRequestData.getDepartmentId() == null)
            detailError.put("departmentId", "Department ID cannot be null");

        if (!detailError.isEmpty()) throw new ApiException(ErrorCode.BAD_REQUEST, detailError);

        return true;
    }

}
