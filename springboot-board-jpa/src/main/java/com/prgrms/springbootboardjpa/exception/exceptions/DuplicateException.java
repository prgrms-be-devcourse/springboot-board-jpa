package com.prgrms.springbootboardjpa.exception.exceptions;

import org.springframework.http.HttpStatus;

public class DuplicateException extends CustomRuntimeException{
    public DuplicateException(String errorDetails) {
        super(HttpStatus.CONFLICT.value(), errorDetails);
    }
}
