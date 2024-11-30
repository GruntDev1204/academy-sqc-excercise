package com.grunt_dev.project_main.request.impl;

import com.grunt_dev.project_main.dto.code_status.impl.ErrorCode;
import com.grunt_dev.project_main.dto.exception.ApiException;
import com.grunt_dev.project_main.model.Employee;
import com.grunt_dev.project_main.request.InterfaceRequest;
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

        if (requestData.getGender() == null) {
            detailError.put("gender", "Gender cannot be null!");
        } else if (!requestData.getGender().equalsIgnoreCase("Male") && !requestData.getGender().equalsIgnoreCase("Female")) {
            detailError.put("gender", "Gender must be either 'Male' or 'Female'!");
        }

        if (requestData.getSalary() <= 0) detailError.put("salary", "Salary must be greater than zero");

        if (requestData.getPhoneNumber() == null || requestData.getPhoneNumber().isEmpty())
            detailError.put("phoneNumber", "Phone number cannot be empty");
        else if (requestData.getPhoneNumber().length() != 10)
            detailError.put("phoneNumber", "Phone number must be 10 characters");

        if (!detailError.isEmpty()) throw new ApiException(ErrorCode.CREATE_FAILED, detailError);

        return true;
    }

}
