package com.prgrms.springbootboardjpa.exception.exceptions;

import org.springframework.http.HttpStatus;

public class NoSuchResourceException extends CustomRuntimeException{
    public NoSuchResourceException(String errorDetails) {
        super(HttpStatus.NOT_FOUND.value(), errorDetails);
    }
}
