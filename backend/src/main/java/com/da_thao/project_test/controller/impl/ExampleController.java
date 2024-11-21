package com.da_thao.project_test.controller.impl;

import com.da_thao.project_test.controller.RestControllerInterface;
import com.da_thao.project_test.dto.ApiResponse;
import com.da_thao.project_test.model.Example;
import com.da_thao.project_test.request_param.ExampleRequest;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/example")
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ExampleController implements RestControllerInterface<Example, ExampleRequest> {

    @GetMapping
    @Override
    public ResponseEntity<ApiResponse<List<Example>>> getAll(ExampleRequest requestParam) {
        return null;
    }

    @GetMapping("/{id}")
    @Override
    public ResponseEntity<ApiResponse<Example>> findById(@PathVariable Integer id) {
        return null;
    }

    @PostMapping
    @Override
    public ResponseEntity<ApiResponse<Example>> create(Example entity) {
        return null;
    }

    @DeleteMapping("/{id}")
    @Override
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable Integer id) {
        return null;
    }

    @PutMapping("/{id}")
    @Override
    public ResponseEntity<ApiResponse<Example>> update(@PathVariable Integer id, Example entity) {
        return null;
    }
}
