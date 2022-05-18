package com.kdt.springbootboardjpa.exception;

public class UnAuthorizationAccessException extends RuntimeException {
    private final String message;

    public UnAuthorizationAccessException(String message) {
        this.message = message;
    }
}
