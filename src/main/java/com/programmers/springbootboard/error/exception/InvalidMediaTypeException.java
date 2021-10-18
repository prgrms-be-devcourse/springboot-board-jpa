package com.programmers.springbootboard.error.exception;

import com.programmers.springbootboard.error.ErrorMessage;
import org.springframework.web.HttpMediaTypeException;

public class InvalidMediaTypeException extends HttpMediaTypeException {
    public InvalidMediaTypeException(ErrorMessage message) {
        super(message.message());
    }
}
