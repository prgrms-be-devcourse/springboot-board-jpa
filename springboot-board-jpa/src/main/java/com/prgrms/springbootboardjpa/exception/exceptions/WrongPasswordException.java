package com.prgrms.springbootboardjpa.exception.exceptions;

import org.springframework.http.HttpStatus;

public class WrongPasswordException extends CustomRuntimeException{
    public WrongPasswordException(String errorDetails) {
        super(HttpStatus.NOT_FOUND.value(), errorDetails);
    }
}
