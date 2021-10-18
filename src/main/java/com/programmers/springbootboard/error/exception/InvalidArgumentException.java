package com.programmers.springbootboard.error.exception;

import com.programmers.springbootboard.error.ErrorMessage;

public class InvalidArgumentException extends RuntimeException {
    public InvalidArgumentException(ErrorMessage message) {
        super(message.message());
    }
}
