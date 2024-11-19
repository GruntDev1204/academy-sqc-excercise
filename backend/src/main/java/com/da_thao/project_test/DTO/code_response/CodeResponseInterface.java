package com.da_thao.project_test.DTO.code_response;

import org.springframework.http.HttpStatus;

public interface CodeResponseInterface {
    Integer getCode();
    String getMessage();
    HttpStatus getStatus();
}


