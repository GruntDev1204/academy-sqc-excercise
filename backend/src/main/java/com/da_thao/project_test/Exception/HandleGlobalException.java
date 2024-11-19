package com.da_thao.project_test.Exception;


import com.da_thao.project_test.DTO.ApiResponse;
import com.da_thao.project_test.DTO.code_response.impl.ErrorCode;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class HandleGlobalException {
    // Handler chung cho các lỗi Exception khác
    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleException(Exception e) {
        // Có thể log lại lỗi hoặc tạo response trả về
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                ApiResponse.builder()
                        .code(500)
                        .message("An unexpected error occurred: " + e.getMessage())
                        .build());
    }

    // Handler cho ApiException (có xử lý trường details)
    @ExceptionHandler(ApiException.class)
    public ResponseEntity<?> handleApiException(ApiException e) {
        ErrorCode errorCode = e.getErrorCode();

        // Kiểm tra xem có `details` không
        if (e.getDetails() != null && !e.getDetails().isEmpty()) {
            return ResponseEntity.status(errorCode.getStatus()).body(
                    ApiResponse.builder()
                            .code(errorCode.getCode())
                            .message(errorCode.getMessage())
                            .data(e.getDetails())  // Thêm thông tin chi tiết vào response
                            .build());
        } else {
            // Nếu không có `details`, chỉ trả về mã lỗi và thông điệp
            return ResponseEntity.status(errorCode.getStatus()).body(
                    ApiResponse.builder()
                            .code(errorCode.getCode())
                            .message(errorCode.getMessage())
                            .build());
        }
    }
}

