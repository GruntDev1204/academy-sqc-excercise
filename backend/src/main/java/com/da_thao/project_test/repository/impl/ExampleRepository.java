package com.da_thao.project_test.repository.impl;

import com.da_thao.project_test.model.Example;
import com.da_thao.project_test.request_param.ExampleRequest;
import com.da_thao.project_test.repository.InterfaceRepository;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

import java.util.List;

@Primary
@Repository
public class ExampleRepository implements InterfaceRepository<Example, ExampleRequest> {

    @Override
    public List<Example> getAll(ExampleRequest requestParam) {
        return List.of();
    }

    @Override
    public Example findById(Long id) {
        return null;
    }

    @Override
    public Example create(Example example) {
        return null;
    }

    @Override
    public Boolean delete(Long id) {
        return null;
    }

    @Override
    public Example update(Long id, Example data) {
        return null;
    }
}
