package com.programmers.springbootboard.exception;

public class NotFoundException extends RuntimeException {
    public NotFoundException(ErrorMessage message) {
        super(message.message());
    }
}
