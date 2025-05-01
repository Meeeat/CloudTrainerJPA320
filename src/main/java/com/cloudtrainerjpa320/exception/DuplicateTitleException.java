package com.cloudtrainerjpa320.exception;

public class DuplicateTitleException extends ApplicationException {
    private static final String ERROR_CODE = "409";

    public DuplicateTitleException(String title) {
        super("Tweet with title '" + title + "' already exists", ERROR_CODE);
    }
}