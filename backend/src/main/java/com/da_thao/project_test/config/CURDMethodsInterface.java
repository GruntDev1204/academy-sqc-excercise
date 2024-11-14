package com.da_thao.project_test.config;

import org.springframework.http.ResponseEntity;
import java.util.List;
import java.util.Map;

public interface CURDMethodsInterface<T> {
    ResponseEntity<List<T>> getAll();
    ResponseEntity<?> create(T entity);
    ResponseEntity<T> getById(int id);
    ResponseEntity<?> delete(int id);
    ResponseEntity<?> update(int id, T entity);
}
