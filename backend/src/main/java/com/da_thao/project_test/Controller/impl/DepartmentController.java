package com.da_thao.project_test.Controller.impl;

import com.da_thao.project_test.Controller.RestControllerInterface;
import com.da_thao.project_test.DTO.ApiResponse;
import com.da_thao.project_test.DTO.code_response.impl.SuccessCode;
import com.da_thao.project_test.DTO.response_data.*;
import com.da_thao.project_test.Model.Department;
import com.da_thao.project_test.Service.InterfaceService;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/departments")
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE , makeFinal = true )
public class DepartmentController implements RestControllerInterface<Department , Object> {
    InterfaceService<Department , Object> departmentService;
    HandleResponseData res;

    @GetMapping
    @Override
    public ResponseEntity<ApiResponse<List<Department>>> getAll(Object requestParam) {
        return res.createSuccessResponse(SuccessCode.DEPARTMENT_GET_ALL_SUCCESS , departmentService.getAll(requestParam));
    }

    @GetMapping("/{id}")
    @Override
    public ResponseEntity<ApiResponse<Department>> findById(@PathVariable Integer id) {
        return res.createSuccessResponse(SuccessCode.DEPARTMENT_GET_BY_VALUE_SUCCESS, departmentService.findById(id));
    }

    @PostMapping
    @Override
    public ResponseEntity<ApiResponse<Department>> create(@RequestBody Department department) {
        return res.createSuccessResponse(SuccessCode.DEPARTMENT_CREATE_SUCCESS ,  departmentService.create(department));
    }

    @Override
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable Integer id) {
           Boolean action = departmentService.delete(id);

           if(action)   return res.createSuccessResponse(SuccessCode.EMPLOYEES_DELETED_SUCCESS);
           else return null;
    }

    @Override
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<Department>> update(@PathVariable Integer id, @RequestBody Department updateDepartment) {
        return res.createSuccessResponse(SuccessCode.DEPARTMENT_UPDATE_SUCCESS, departmentService.update(id,updateDepartment));
    }

}
