package com.grunt_dev.project_main.dto.code_status.impl;

import com.grunt_dev.project_main.dto.code_status.CodeResponseInterface;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PROTECTED, makeFinal = true)
@Getter
public enum ErrorCode implements CodeResponseInterface {
    // General error codes
    GENERAL_GET_FAILED(50001, "FAILED TO RETRIEVE DATA!", HttpStatus.INTERNAL_SERVER_ERROR),
    GENERAL_NOT_FOUND(40400, "DATA NOT FOUND!", HttpStatus.NOT_FOUND),
    DELETED_FAILED(40001, "FAILED TO DELETE DATA! ", HttpStatus.NO_CONTENT),

    // POST/CREATE method
    CREATE_FAILED(42200, "数据无效！请检查输入字段!", HttpStatus.UNPROCESSABLE_ENTITY),
    BAD_REQUEST(40000, "数据无效！请检查输入字段!", HttpStatus.BAD_REQUEST),

    // PUT/UPDATE method
    UPDATE_FAILED(42201, "FAILED TO UPDATE DATA! INVALID INPUT DATA!", HttpStatus.BAD_REQUEST),

    ;
    Integer code;
    String message;
    HttpStatus status;

}