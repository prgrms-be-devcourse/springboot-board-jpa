package com.programmers.springbootboard.exception.error;

import com.programmers.springbootboard.exception.ErrorMessage;
import org.springframework.web.HttpMediaTypeException;

// TODO Exception이 제대로 터지는지 확인 필요
public class InvalidMediaTypeException extends HttpMediaTypeException {
    public InvalidMediaTypeException(ErrorMessage message) {
        super(message.message());
    }
}
