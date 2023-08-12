package com.example.jpaboard.global.exception;

public class UnauthorizedEditException extends RuntimeException {

    public UnauthorizedEditException(String message) {
        super(message);
    }

}
