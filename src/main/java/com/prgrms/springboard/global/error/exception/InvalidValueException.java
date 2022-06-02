package com.prgrms.springboard.global.error.exception;

import org.springframework.http.HttpStatus;

public class InvalidValueException extends BusinessException {

    public InvalidValueException(String message) {
        super(message, HttpStatus.BAD_REQUEST);
    }

    public InvalidValueException(String message, HttpStatus httpStatus) {
        super(message, httpStatus);
    }
}
