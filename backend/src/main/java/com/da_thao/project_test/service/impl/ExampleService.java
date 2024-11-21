package com.da_thao.project_test.service.impl;

import com.da_thao.project_test.model.Example;
import com.da_thao.project_test.request_param.ExampleRequest;
import com.da_thao.project_test.repository.InterfaceRepository;
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
public class ExampleService implements InterfaceService<Example, ExampleRequest> {
    InterfaceRepository<Example, ExampleRequest> ExampleRepository;


    @Override
    public List<Example> getAll(ExampleRequest requestParam) {
        return List.of();
    }

    @Override
    public Example findById(Integer id) {
        return null;
    }

    @Override
    public Example create(Example e) {
        return null;
    }

    @Override
    public Boolean delete(Integer id) {
        return null;
    }

    @Override
    public Example update(Integer id, Example data) {
        return null;
    }
}
