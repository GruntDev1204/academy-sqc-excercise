package com.da_thao.project_test.controller.impl;

import com.da_thao.project_test.controller.RestControllerInterface;
import com.da_thao.project_test.dto.ApiResponse;
import com.da_thao.project_test.dto.code_response.impl.SuccessCode;
import com.da_thao.project_test.dto.response_data.*;
import com.da_thao.project_test.model.Department;
import com.da_thao.project_test.service.InterfaceService;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/departments")
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class DepartmentController implements RestControllerInterface<Department, Object> {
    InterfaceService<Department, Object> departmentService;
    HandleResponseData res;

    @GetMapping
    @Override
    public ResponseEntity<ApiResponse<List<Department>>> getAll(Object requestParam) {
        return res.returnResponseJson(SuccessCode.GET_ALL_SUCCESS, departmentService.getAll(requestParam));
    }

    @GetMapping("/{id}")
    @Override
    public ResponseEntity<ApiResponse<Department>> findById(@PathVariable Integer id) {
        return res.returnResponseJson(SuccessCode.GET_BY_ID_SUCCESS, departmentService.findById(id));
    }

    @PostMapping
    @Override
    public ResponseEntity<ApiResponse<Department>> create(@RequestBody Department department) {
        return res.returnResponseJson(SuccessCode.CREATE_SUCCESS, departmentService.create(department));
    }

    @Override
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable Integer id) {
        Boolean action = departmentService.delete(id);

        if (action) return res.returnResponseJson(SuccessCode.DELETE_SUCCESS);
        else return null;
    }

    @Override
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<Department>> update(@PathVariable Integer id, @RequestBody Department updateDepartment) {
        return res.returnResponseJson(SuccessCode.UPDATE_SUCCESS, departmentService.update(id, updateDepartment));
    }

}
