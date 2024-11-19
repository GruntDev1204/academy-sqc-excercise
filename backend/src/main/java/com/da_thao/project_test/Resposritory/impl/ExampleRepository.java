package com.da_thao.project_test.Resposritory.impl;

import com.da_thao.project_test.Model.Example;
import com.da_thao.project_test.Request_param.ExampleRequest;
import com.da_thao.project_test.Resposritory.InterfaceRepository;
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
    public Example findById(Integer id) {
        return null;
    }

    @Override
    public Example create(Example example) {
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
