package com.da_thao.project_test.service;

import java.util.List;

public interface InterfaceService<M ,R> {
     List<M> getAll(R  requestParam);
     M findById(Integer id);
     M create(M model);
     Boolean delete(Integer id);
     M update(Integer id , M model);
}


