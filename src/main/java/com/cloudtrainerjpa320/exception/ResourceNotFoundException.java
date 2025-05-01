package com.cloudtrainerjpa320.exception;

public class ResourceNotFoundException extends ApplicationException {

    private static final String ERROR_CODE = "404";

    public ResourceNotFoundException(String message) {
        super(message, ERROR_CODE);
    }

    public ResourceNotFoundException(String message, Throwable cause) {
        super(message, ERROR_CODE, cause);
    }
}