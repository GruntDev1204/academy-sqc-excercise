package com.da_thao.project_test.dto.code_response.impl;

import com.da_thao.project_test.dto.code_response.CodeResponseInterface;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PROTECTED, makeFinal = true)
@Getter
public enum ErrorCode implements CodeResponseInterface {
    //get methods
    EMPLOYEES_GET_ALL_FAILED(50001, "CANNOT GET THE EMPLOYEES DATA!", HttpStatus.INTERNAL_SERVER_ERROR),
    DEPARTMENT_GET_ALL_FAILED(50002, "CANNOT GET THE DEPARTMENT DATA!", HttpStatus.INTERNAL_SERVER_ERROR),

    EMPLOYEES_NOT_FOUND(40400, "EMPLOYEE NOT FOUND!", HttpStatus.NOT_FOUND),
    DEPARTMENT_NOT_FOUND(40402, "DEPARTMENT NOT FOUND!", HttpStatus.NOT_FOUND),


    //post method
    CREATE_FAILED(422, "数据无效！请检查输入字段!", HttpStatus.BAD_REQUEST),
    EMPLOYEES_CREATE_FAILED(42201, "数据无效！请检查输入字段!", HttpStatus.BAD_REQUEST),
    EMPLOYEES_UPDATE_FAILED(42202, "FAILED TO UPDATE EMPLOYEE! INVALID INPUT DATA!", HttpStatus.BAD_REQUEST),
    EMPLOYEES_DELETE_FAILED(42203, "FAILED TO DELETE EMPLOYEE! ID DOES NOT EXIST!", HttpStatus.BAD_REQUEST),

    ;
    Integer code;
    String message;
    HttpStatus status;

}

