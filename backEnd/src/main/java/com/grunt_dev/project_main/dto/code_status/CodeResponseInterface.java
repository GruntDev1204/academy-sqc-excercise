package com.grunt_dev.project_main.dto.code_status;

import org.springframework.http.HttpStatus;

public interface CodeResponseInterface {
    Integer getCode();
    String getMessage();
    HttpStatus getStatus();
}