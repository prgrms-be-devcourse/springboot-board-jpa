package com.prgrms.springboard.global.error.exception;

public class InvalidValueException extends IllegalArgumentException {
    public InvalidValueException(String message) {
        super(message);
    }
}
