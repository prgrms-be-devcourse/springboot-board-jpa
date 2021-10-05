package com.programmers.springbootboard.exception.error;

import com.programmers.springbootboard.exception.ErrorMessage;

public class DuplicationArgumentException extends RuntimeException {
    public DuplicationArgumentException(ErrorMessage message) {
        super(message.message());
    }
}
