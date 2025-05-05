package com.pfms.pfms_analytics.enums;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorCodeEnum {

    VALIDATION_ERROR( "Validation failed", HttpStatus.BAD_REQUEST),
    ALREADY_EXISTS( "Resource already exists", HttpStatus.CONFLICT),
    NOT_FOUND( "Resource not found", HttpStatus.NOT_FOUND),
    UNAUTHORIZED( "You are not authorized to access this resource", HttpStatus.UNAUTHORIZED),
    FORBIDDEN( "Access is forbidden", HttpStatus.FORBIDDEN),
    INTERNAL_ERROR( "An unexpected error occurred", HttpStatus.INTERNAL_SERVER_ERROR),
    CUSTOM_VALIDATION( "Business rule validation failed", HttpStatus.BAD_REQUEST);

    private final HttpStatus code;
    private final String defaultMessage;

    ErrorCodeEnum(String defaultMessage, HttpStatus code) {
        this.code = code;
        this.defaultMessage = defaultMessage;
    }
}
