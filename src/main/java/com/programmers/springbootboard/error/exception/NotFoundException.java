package com.programmers.springbootboard.error.exception;

import com.programmers.springbootboard.error.ErrorMessage;

public class NotFoundException extends RuntimeException {
    public NotFoundException(ErrorMessage message) {
        super(message.message());
    }
}
