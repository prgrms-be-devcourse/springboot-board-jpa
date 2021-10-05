package com.programmers.springbootboard.exception.error;

import com.programmers.springbootboard.exception.ErrorMessage;

public class InvalidArgumentException extends RuntimeException {
    public InvalidArgumentException(ErrorMessage message) {
        super(message.message());
    }
}
