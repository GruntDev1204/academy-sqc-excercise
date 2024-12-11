package com.grunt_dev.project_main.dto.response_json;

import com.grunt_dev.project_main.dto.ApiResponse;
import com.grunt_dev.project_main.dto.code_status.impl.SuccessCode;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class HandleResponseData {
    @SuppressWarnings("unchecked")
    public <T> ApiResponse<T> customPage(SuccessCode successCode, Page<T> page) {
        return ApiResponse.<T>builder()
                .code(successCode.getCode())
                .message(successCode.getMessage())
                .data((T) page.getContent())
                .pageDetail(Map.of(
                        "total_items", page.getTotalElements(),
                        "total_pages", page.getTotalPages(),
                        "size_of_a_page", page.getSize(),
                        "index_page", page.getNumber() + 1
                ))
                .build();
    }

    @SuppressWarnings("unchecked")
    public <T> ResponseEntity<ApiResponse<T>> returnResponseJson(SuccessCode successCode, T data) {
        ApiResponse<T> response;
        if (data == null) {
            response = ApiResponse.<T>builder()
                    .code(successCode.getCode())
                    .message(successCode.getMessage())
                    .data(null)
                    .pageDetail(null)
                    .build();
        }

        assert data != null;
        if (data instanceof Page<?> page) {
            response = (ApiResponse<T>) this.customPage(successCode, page);
        } else {
            response = ApiResponse.<T>builder()
                    .code(successCode.getCode())
                    .message(successCode.getMessage())
                    .data((T) data)
                    .pageDetail(null)
                    .build();
        }
        return ResponseEntity.status(successCode.getStatus()).body(response);
    }

    public ResponseEntity<ApiResponse<Void>> returnResponseJson(SuccessCode successCode) {
        return this.returnResponseJson(successCode, null);
    }
}