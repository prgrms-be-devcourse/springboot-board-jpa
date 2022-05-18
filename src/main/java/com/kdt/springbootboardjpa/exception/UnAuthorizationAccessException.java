package com.kdt.springbootboardjpa.exception;


public class UnAuthorizationAccessException extends RuntimeException {
    public UnAuthorizationAccessException(String message) {
        super(message);
    }
}
