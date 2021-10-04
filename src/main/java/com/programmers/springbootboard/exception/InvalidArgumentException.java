package com.programmers.springbootboard.exception;

public class InvalidArgumentException extends RuntimeException {
    public InvalidArgumentException(ErrorMessage message) {
        super(message.message());
    }
}
