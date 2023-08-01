package com.programmers.springbootboardjpa.global.error.exception;

public class InvalidEntityValueException extends IllegalArgumentException {

    public InvalidEntityValueException(String message) {
        super(message);
    }
}
