package com.grunt_dev.project_main.dto.response_json;

import com.grunt_dev.project_main.dto.ApiResponse;
import com.grunt_dev.project_main.dto.code_status.impl.SuccessCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public class HandleResponseData {

    public <T> ResponseEntity<ApiResponse<T>> returnResponseJson(SuccessCode successCode, T data) {
        ApiResponse<T> response = ApiResponse.<T>builder()
                .code(successCode.getCode())
                .message(successCode.getMessage())
                .data(data)
                .build();
        return ResponseEntity.status(successCode.getStatus()).body(response);
    }

    public ResponseEntity<ApiResponse<Void>> returnResponseJson(SuccessCode successCode) {
        return this.returnResponseJson(successCode, null);
    }

}