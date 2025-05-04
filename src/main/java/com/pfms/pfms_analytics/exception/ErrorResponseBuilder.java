package com.pfms.pfms_analytics.exception;

import com.pfms.pfms_analytics.enums.ErrorCodeEnum;

import jakarta.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.List;

public class ErrorResponseBuilder {

    private ErrorResponseBuilder() {
        throw new UnsupportedOperationException("Utility class should not be instantiated");
    }

    public static ErrorResponseDto build(HttpServletRequest request,
                                         ErrorCodeEnum codeEnum,
                                         String customMessage,
                                         List<ValidationException> validationErrors) {

        return ErrorResponseDto.builder()
                .apiPath(request.getRequestURI())
                .errorCode(codeEnum.getCode())
                .errorMessage(customMessage != null ? customMessage : codeEnum.getDefaultMessage())
                .errorTime(LocalDateTime.now())
                .validationException(validationErrors != null && !validationErrors.isEmpty() ? validationErrors : null)
                .build();
    }
}
