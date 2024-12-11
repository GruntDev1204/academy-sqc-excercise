package com.grunt_dev.project_main.dto.code_status.impl;

import com.grunt_dev.project_main.dto.code_status.CodeResponseInterface;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public enum SuccessCode implements CodeResponseInterface {
    // Success codes for GET operations
    GET_ALL_SUCCESS(20001, "Successfully retrieved all DATA!", HttpStatus.OK),
    GET_BY_ID_SUCCESS(20002, "Successfully retrieved the requested DATA!", HttpStatus.OK),

    // Success codes for POST/CREATE operations
    CREATE_SUCCESS(20100, "Successfully created the DATA!", HttpStatus.CREATED),

    // Success codes for PUT/UPDATE operations
    UPDATE_SUCCESS(20003, "Successfully updated the DATA!", HttpStatus.OK),

    // Success codes for DELETE operations
    DELETE_SUCCESS(20404, "Successfully deleted the DATA!", HttpStatus.OK),

    ;

    Integer code;
    String message;
    HttpStatus status;

}
