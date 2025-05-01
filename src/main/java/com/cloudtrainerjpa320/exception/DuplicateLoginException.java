package com.cloudtrainerjpa320.exception;

public class DuplicateLoginException extends ApplicationException {
    private static final String ERROR_CODE = "403";

    public DuplicateLoginException(String login) {
        super("Login '" + login + "' already exists", ERROR_CODE);
    }
}