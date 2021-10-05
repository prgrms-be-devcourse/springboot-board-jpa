package com.programmers.springbootboard.exception.error;

import com.programmers.springbootboard.exception.ErrorMessage;

public class NotFoundException extends RuntimeException {
    public NotFoundException(ErrorMessage message) {
        super(message.message());
    }
}
