package com.cloudtrainerjpa320.exception;

public class DataConflictException extends ApplicationException {

    private static final String ERROR_CODE = "409";

    public DataConflictException(String message) {
        super(message, ERROR_CODE);
    }

    public DataConflictException(String message, Throwable cause) {
        super(message, ERROR_CODE, cause);
    }
}