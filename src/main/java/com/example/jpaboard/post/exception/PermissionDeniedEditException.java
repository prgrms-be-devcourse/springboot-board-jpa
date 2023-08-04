package com.example.jpaboard.post.exception;

public class PermissionDeniedEditException extends RuntimeException{
    public PermissionDeniedEditException(String message) {
        super(message);
    }
}
