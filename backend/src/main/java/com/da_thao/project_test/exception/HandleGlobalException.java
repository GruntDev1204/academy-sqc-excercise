package com.da_thao.project_test.exception;


import com.da_thao.project_test.dto.ApiResponse;
import com.da_thao.project_test.dto.code_response.impl.ErrorCode;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.sql.SQLException;

@ControllerAdvice
public class HandleGlobalException {
    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleException(Exception e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                ApiResponse.builder()
                        .code(500)
                        .message("An unexpected error occurred: " + e.getMessage())
                        .build());
    }

    @ExceptionHandler(ApiException.class)
    public ResponseEntity<?> handleApiException(ApiException e) {
        ErrorCode errorCode = e.getErrorCode();

        if (e.getDetails() != null && !e.getDetails().isEmpty()) {
            return ResponseEntity.status(errorCode.getStatus()).body(
                    ApiResponse.builder()
                            .code(errorCode.getCode())
                            .message(errorCode.getMessage())
                            .data(e.getDetails())
                            .build());
        } else {
            return ResponseEntity.status(errorCode.getStatus()).body(
                    ApiResponse.builder()
                            .code(errorCode.getCode())
                            .message(errorCode.getMessage())
                            .build());
        }
    }

    @ExceptionHandler(SQLException.class)
    public ResponseEntity<?> handleSQLException(SQLException e) {
        String errorMessage = "Database error occurred: " + e.getMessage();
        String sqlState = e.getSQLState();
        int errorCode = e.getErrorCode();
        Throwable cause = e.getCause();

        String detailedMessage = String.format("SQLState: %s, ErrorCode: %d, Message: %s", sqlState, errorCode, errorMessage);

        if (cause != null) {
            detailedMessage += ", Cause: " + cause.toString();
        }

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                ApiResponse.builder()
                        .code(500)
                        .message("A database error occurred: " + e.getMessage())
                        .data(detailedMessage)
                        .build());
    }
}

