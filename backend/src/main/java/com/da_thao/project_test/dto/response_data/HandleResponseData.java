package com.da_thao.project_test.dto.response_data;

import com.da_thao.project_test.dto.ApiResponse;
import com.da_thao.project_test.dto.code_response.impl.SuccessCode;
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
