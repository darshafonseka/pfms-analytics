package com.pfms.pfms_analytics.exception;


public class AlreadyExistsException extends RuntimeException {

    public AlreadyExistsException() {
        super("Resource already exists");
    }

    public AlreadyExistsException(String message) {
        super(message);
    }

    public AlreadyExistsException(String resourceName, Object resourceId) {
        super(String.format("Resource '%s' with ID '%s' already exists", resourceName, resourceId));
    }
}
