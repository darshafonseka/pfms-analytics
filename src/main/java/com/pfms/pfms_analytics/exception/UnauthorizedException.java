package com.pfms.pfms_analytics.exception;

import com.pfms.pfms_analytics.enums.ErrorCodeEnum;

public class UnauthorizedException extends RuntimeException {
    public UnauthorizedException() {
        super(ErrorCodeEnum.NOT_FOUND.getDefaultMessage());
    }
}
