package com.grunt_dev.project_main.dto.exception;

import com.grunt_dev.project_main.dto.code_status.impl.ErrorCode;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

import java.util.Map;

@Getter
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ApiException extends RuntimeException {
    ErrorCode errorCode;
    Map<String, String> details;

    public ApiException(ErrorCode errorCode, Map<String, String> details) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
        this.details = details;
    }

    public ApiException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;

        this.details = Map.of();
    }
}
