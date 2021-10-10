package com.programmers.springbootboard.exception.error;

import com.programmers.springbootboard.exception.ErrorMessage;
import org.springframework.web.HttpMediaTypeException;

// TODO
public class InvalidMediaTypeException extends HttpMediaTypeException {
    public InvalidMediaTypeException(ErrorMessage message) {
        super(message.message());
    }
}
