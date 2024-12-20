package com.da_thao.project_test.Controller;

import com.da_thao.project_test.DTO.ApiResponse;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface RestControllerInterface<M,R> {
    ResponseEntity<ApiResponse<List<M>>> getAll(R requestParam);
    ResponseEntity<ApiResponse<M>> findById(Integer id);
    ResponseEntity<ApiResponse<M>> create(M model);
    ResponseEntity<ApiResponse<Void>> delete(Integer id);
    ResponseEntity<ApiResponse<M>> update(Integer id, M model);
}
