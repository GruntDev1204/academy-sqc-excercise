package com.da_thao.project_test.controller.impl;

import com.da_thao.project_test.controller.RestControllerInterface;
import com.da_thao.project_test.dto.*;
import com.da_thao.project_test.dto.code_response.impl.SuccessCode;
import com.da_thao.project_test.dto.response_data.*;
import com.da_thao.project_test.dto.data_filter.FindEmployee;
import com.da_thao.project_test.service.InterfaceService;
import com.da_thao.project_test.model.Employee;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/employees")
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class EmployeeController implements RestControllerInterface<Employee, FindEmployee> {
    InterfaceService<Employee, FindEmployee> employeeService;
    HandleResponseData res;

    @Override
    @GetMapping("")
    public ResponseEntity<ApiResponse<List<Employee>>> getAll(FindEmployee findEmployee) {
        return res.createSuccessResponse(SuccessCode.EMPLOYEES_GET_ALL_SUCCESS, employeeService.getAll(findEmployee));
    }

    @Override
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Employee>> findById(@PathVariable Integer id) {
        return res.createSuccessResponse(SuccessCode.EMPLOYEES_GET_BY_VALUE_SUCCESS, employeeService.findById(id));
    }

    @Override
    @PostMapping("")
    public ResponseEntity<ApiResponse<Employee>> create(@RequestBody Employee employee) {
        return res.createSuccessResponse(SuccessCode.EMPOYEES_CREATED_SUCCESS, employeeService.create(employee));
    }

    @Override
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable Integer id) {
        Boolean action = employeeService.delete(id);
        if (action) return res.createSuccessResponse(SuccessCode.EMPLOYEES_DELETED_SUCCESS);
        else return null;
    }

    @Override
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<Employee>> update(@PathVariable Integer id, @RequestBody Employee updatedEmployee) {
        return res.createSuccessResponse(SuccessCode.EMPLOYEES_UPDATED_SUCCESS, employeeService.update(id, updatedEmployee));
    }

}
