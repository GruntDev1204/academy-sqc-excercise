package com.da_thao.project_test.Request_param.filter_data.impl;

import com.da_thao.project_test.Model.Department;
import com.da_thao.project_test.Request_param.filter_data.FilterDataInterface;
import lombok.Getter;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Primary
public class FilterDepartmentData implements FilterDataInterface<Department , Object> {

    @Override
    public List<Department> filterAll(List<Department> dataModel, Object dataFilter) {
        return List.of();
    }

    @Override
    public Department filterById(List<Department> dataModel, Integer id) {
        return dataModel.stream()
                .filter(data -> data.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

}
