package com.da_thao.project_test.repository;

import java.util.List;

public interface InterfaceRepository<M,R> {
    List<M> getAll(R  requestParam);
    M findById(Long id);
    M create(M data);
    Boolean delete(Long id);
    M update(Long id ,M newData);
}
