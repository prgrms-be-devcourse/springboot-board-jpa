package com.example.jpaboard.global.exception;

public class PermissionDeniedEditException extends RuntimeException {

    public PermissionDeniedEditException(String message) {
        super(message);
    }

}
