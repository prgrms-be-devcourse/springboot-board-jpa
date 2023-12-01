package com.example.board.exception;


import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class BaseException extends RuntimeException {
    private final int code;
    private final String message;
    private final HttpStatus httpStatus;

    public BaseException(ErrorMessage message) {
        this.code = message.getCode();
        this.message = message.getMessage();
        this.httpStatus = message.getHttpStatus();
    }
}
