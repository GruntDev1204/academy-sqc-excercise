package com.da_thao.project_test.request_param.filter_data.impl;

import com.da_thao.project_test.dto.filter_information.FindEmployee;
import com.da_thao.project_test.request_param.filter_data.InterfaceFilterData;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
@Primary
public class EmployeeFilter implements InterfaceFilterData<FindEmployee> {

    @Override
    public QueryFilterData getFilterData(FindEmployee requestParam) {
        StringBuilder queryBuilder = new StringBuilder("WHERE 1=1");
        Map<String, Object> params = new HashMap<>();

        if (requestParam.getFullName() != null && !requestParam.getFullName().isEmpty()) {
            queryBuilder.append(" AND full_name LIKE :fullName");
            params.put("fullName", "%" + requestParam.getFullName() + "%");
        }

        if (requestParam.getDobFrom() != null) {
            queryBuilder.append(" AND birthday >= :dobFrom");
            params.put("dobFrom", requestParam.getDobFrom());
        }

        if (requestParam.getDobTo() != null) {
            queryBuilder.append(" AND birthday <= :dobTo");
            params.put("dobTo", requestParam.getDobTo());
        }

        if (requestParam.getGender() != null && !requestParam.getGender().isEmpty()) {
            queryBuilder.append(" AND gender = :gender");
            params.put("gender", requestParam.getGender());
        }

        if (requestParam.getSalaryValue() != null) {
            switch (requestParam.getSalaryValue()) {
                case "under_10m":
                    queryBuilder.append(" AND salary < 10000000");
                    break;
                case "10_to_15m":
                    queryBuilder.append(" AND salary BETWEEN 10000000 AND 15000000");
                    break;
                case "15_to_20m":
                    queryBuilder.append(" AND salary BETWEEN 15000000 AND 20000000");
                    break;
                case "above_20m":
                    queryBuilder.append(" AND salary > 20000000");
                    break;
                default:
                    break;
            }
        }

        if (requestParam.getPhoneNumber() != null && !requestParam.getPhoneNumber().isEmpty()) {
            queryBuilder.append(" AND phone_number = :phoneNumber");
            params.put("phoneNumber", requestParam.getPhoneNumber());
        }

        if (requestParam.getDepartmentId() != null) {
            queryBuilder.append(" AND department_id = :departmentId");
            params.put("departmentId", requestParam.getDepartmentId());
        }

        return new QueryFilterData(queryBuilder.toString(), params);
    }
}
