package com.grunt_dev.project_main.controller.impl;

import com.grunt_dev.project_main.controller.RestControllerInterface;
import com.grunt_dev.project_main.dto.ApiResponse;
import com.grunt_dev.project_main.dto.code_status.impl.ErrorCode;
import com.grunt_dev.project_main.dto.code_status.impl.SuccessCode;
import com.grunt_dev.project_main.dto.exception.ApiException;
import com.grunt_dev.project_main.dto.response_json.HandleResponseData;
import com.grunt_dev.project_main.model.Department;
import com.grunt_dev.project_main.service.InterfaceService;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/departments")
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class DepartmentController implements RestControllerInterface<Department, Object, Page<Department>> {
    InterfaceService<Department, Object, Page<Department>> service;
    HandleResponseData res;

    @Override
    @GetMapping("")
    public ResponseEntity<ApiResponse<Page<Department>>> getAll(
            Object requestParam,
            @RequestParam(required = false, defaultValue = "1") Integer index,
            @RequestParam(required = false, defaultValue = "5") Integer size
    ) {
        return res.returnResponseJson(SuccessCode.GET_ALL_SUCCESS, service.getAll(requestParam, index - 1, size));
    }

    @Override
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Department>> findById(@PathVariable Long id) {
        return res.returnResponseJson(SuccessCode.GET_BY_ID_SUCCESS, service.findById(id));
    }

    @Override
    @PostMapping("")
    public ResponseEntity<ApiResponse<Department>> create(@RequestBody Department data) {
        return res.returnResponseJson(SuccessCode.CREATE_SUCCESS, service.create(data));
    }

    @Override
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable Long id) {
        Boolean success = service.delete(id);
        if (success) {
            return res.returnResponseJson(SuccessCode.DELETE_SUCCESS);
        } else {
            throw new ApiException(ErrorCode.DELETED_FAILED);
        }
    }

    @Override
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<Department>> update(@PathVariable Long id, @RequestBody Department newData) {
        return res.returnResponseJson(SuccessCode.UPDATE_SUCCESS, service.update(id, newData));
    }
}
