package com.pfms.pfms_analytics.exception;

import com.pfms.pfms_analytics.enums.ErrorCodeEnum;

public class ResourceNotFoundException extends RuntimeException {

    public ResourceNotFoundException() {
        super(ErrorCodeEnum.NOT_FOUND.getDefaultMessage());
    }

    public ResourceNotFoundException(String message) {
        super(message);
    }

    public ResourceNotFoundException(String resourceName, Object resourceId) {
        super(String.format("Resource '%s' with ID '%s' not found", resourceName, resourceId));
    }
}