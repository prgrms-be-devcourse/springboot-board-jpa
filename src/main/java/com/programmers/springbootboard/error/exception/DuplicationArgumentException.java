package com.programmers.springbootboard.error.exception;

import com.programmers.springbootboard.error.ErrorMessage;

public class DuplicationArgumentException extends RuntimeException {
    public DuplicationArgumentException(ErrorMessage message) {
        super(message.message());
    }
}
