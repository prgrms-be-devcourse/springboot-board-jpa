package com.programmers.springbootboard.exception;

public class InvalidArgumentException extends IllegalArgumentException {
    public InvalidArgumentException(ErrorMessage message) {
        super(message.message());
    }
}
