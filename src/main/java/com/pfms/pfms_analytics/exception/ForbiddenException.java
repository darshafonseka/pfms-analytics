package com.pfms.pfms_analytics.exception;

import com.pfms.pfms_analytics.enums.ErrorCodeEnum;

public class ForbiddenException extends RuntimeException {
    public ForbiddenException() {
        super(ErrorCodeEnum.NOT_FOUND.getDefaultMessage());
    }
    public ForbiddenException(String message) {
        super(message);
    }
}