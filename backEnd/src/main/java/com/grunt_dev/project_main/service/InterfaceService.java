package com.grunt_dev.project_main.service;

public interface InterfaceService<M, R, T> {
    T getAll(R requestParam, Integer index, Integer size);

    M findById(Long id);

    M create(M data);

    Boolean delete(Long id);

    M update(Long id, M newData);
}

