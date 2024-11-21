package com.da_thao.project_test.dto.code_response.impl;

import com.da_thao.project_test.dto.code_response.CodeResponseInterface;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
@FieldDefaults(level =  AccessLevel.PRIVATE , makeFinal = true)
public enum SuccessCode implements CodeResponseInterface {
    EMPLOYEES_GET_ALL_SUCCESS(20001 , "Success" , HttpStatus.OK),
    EMPLOYEES_GET_BY_VALUE_SUCCESS(20002 , "Object get Successfully" , HttpStatus.OK),
    EMPOYEES_CREATED_SUCCESS(20101 , "Created Employee Successfully!" , HttpStatus.CREATED),
    EMPLOYEES_UPDATED_SUCCESS(20002, "UPDATED EMPLOYEE SUCCESSFULLY!", HttpStatus.OK),
    EMPLOYEES_DELETED_SUCCESS(20003, "DELETED EMPLOYEE SUCCESSFULLY!", HttpStatus.OK),

    DEPARTMENT_GET_ALL_SUCCESS(20010, " DEPARTMENT GET SUCCESSFULLY!", HttpStatus.OK),
    DEPARTMENT_GET_BY_VALUE_SUCCESS(20011, "Object DEPARTMENT GET SUCCESSFULLY!", HttpStatus.OK),
    DEPARTMENT_CREATE_SUCCESS(20102, "Created DEPARTMENT SUCCESSFULLY!", HttpStatus.CREATED),
    DEPARTMENT_DELETE_SUCCESS(20012, "Deleted DEPARTMENT SUCCESSFULLY!", HttpStatus.OK),
    DEPARTMENT_UPDATE_SUCCESS(20012, "Updated DEPARTMENT SUCCESSFULLY!", HttpStatus.CREATED),

    ;

   Integer code;
   String message;
   HttpStatus status;

}


