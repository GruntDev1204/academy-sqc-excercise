package com.da_thao.project_test.controller.impl;

import com.da_thao.project_test.controller.RestControllerInterface;
import com.da_thao.project_test.dto.*;
import com.da_thao.project_test.dto.code_response.impl.ErrorCode;
import com.da_thao.project_test.dto.code_response.impl.SuccessCode;
import com.da_thao.project_test.dto.response_data.*;
import com.da_thao.project_test.dto.filter_information.FindEmployee;
import com.da_thao.project_test.exception.ApiException;
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
    public ResponseEntity<ApiResponse<List<Employee>>> getAll(FindEmployee requestParam) {
        return res.returnResponseJson(SuccessCode.GET_ALL_SUCCESS, employeeService.getAll(requestParam));
    }

    @Override
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Employee>> findById(@PathVariable Integer id) {
        return res.returnResponseJson(SuccessCode.GET_BY_ID_SUCCESS, employeeService.findById(id));
    }

    @Override
    @PostMapping("")
    public ResponseEntity<ApiResponse<Employee>> create(@RequestBody Employee data) {
        return res.returnResponseJson(SuccessCode.CREATE_SUCCESS, employeeService.create(data));
    }

    @Override
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable Integer id) {
        Boolean action = employeeService.delete(id);
        if (action) return res.returnResponseJson(SuccessCode.DELETE_SUCCESS);
        else throw new ApiException(ErrorCode.DELETED_FAILED);
    }

    @Override
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<Employee>> update(@PathVariable Integer id, @RequestBody Employee newData) {
        return res.returnResponseJson(SuccessCode.UPDATE_SUCCESS, employeeService.update(id, newData));
    }

}
