package com.programmers.springbootboard.exception;

public class DuplicationArgumentException extends RuntimeException {
    public DuplicationArgumentException(ErrorMessage message) {
        super(message.message());
    }
}
