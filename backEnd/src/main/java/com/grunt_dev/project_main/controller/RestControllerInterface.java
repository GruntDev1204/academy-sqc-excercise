package com.grunt_dev.project_main.controller;

import com.grunt_dev.project_main.dto.ApiResponse;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface RestControllerInterface<M, R> {
    ResponseEntity<ApiResponse<List<M>>> getAll(R requestParam);

    ResponseEntity<ApiResponse<M>> findById(Long id);

    ResponseEntity<ApiResponse<M>> create(M data);

    ResponseEntity<ApiResponse<Void>> delete(Long id);

    ResponseEntity<ApiResponse<M>> update(Long id, M newData);
}