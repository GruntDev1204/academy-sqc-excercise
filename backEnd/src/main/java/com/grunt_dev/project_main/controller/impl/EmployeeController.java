package com.grunt_dev.project_main.controller.impl;

import com.grunt_dev.project_main.attribute.attribute_class.FindEmployee;
import com.grunt_dev.project_main.controller.RestControllerInterface;
import com.grunt_dev.project_main.dto.ApiResponse;
import com.grunt_dev.project_main.dto.code_status.impl.ErrorCode;
import com.grunt_dev.project_main.dto.code_status.impl.SuccessCode;
import com.grunt_dev.project_main.dto.exception.ApiException;
import com.grunt_dev.project_main.dto.response_json.HandleResponseData;
import com.grunt_dev.project_main.model.Employee;
import com.grunt_dev.project_main.service.InterfaceService;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/employees")
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class EmployeeController implements RestControllerInterface<Employee, FindEmployee, List<Employee>> {
    InterfaceService<Employee, FindEmployee, List<Employee>> employeeService;
    HandleResponseData res;

    @Override
    @GetMapping("")
    public ResponseEntity<ApiResponse<List<Employee>>> getAll(
            FindEmployee requestParam,
            @RequestParam(required = false) Integer index,
            @RequestParam(required = false) Integer size
    ) {
        return res.returnResponseJson(SuccessCode.GET_ALL_SUCCESS, employeeService.getAll(requestParam, index, size));
    }

    @Override
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Employee>> findById(@PathVariable Long id) {
        return res.returnResponseJson(SuccessCode.GET_BY_ID_SUCCESS, employeeService.findById(id));
    }

    @Override
    @PostMapping("")
    public ResponseEntity<ApiResponse<Employee>> create(@RequestBody Employee data) {
        return res.returnResponseJson(SuccessCode.CREATE_SUCCESS, employeeService.create(data));
    }

    @Override
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable Long id) {
        Boolean action = employeeService.delete(id);
        if (action) return res.returnResponseJson(SuccessCode.DELETE_SUCCESS);
        else throw new ApiException(ErrorCode.DELETED_FAILED);
    }

    @Override
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<Employee>> update(@PathVariable Long id, @RequestBody Employee newData) {
        return res.returnResponseJson(SuccessCode.UPDATE_SUCCESS, employeeService.update(id, newData));
    }

}
