package com.programmers.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.NoSuchElementException;

@RestControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    private ErrorResult handleException(Exception e) {
        return new ErrorResult(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage());
    }

    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    private ErrorResult handleIllegalArgumentException(IllegalArgumentException e) {
        return new ErrorResult(HttpStatus.BAD_REQUEST.value(), e.getMessage());
    }

    @ExceptionHandler(NoSuchElementException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    private ErrorResult handleNoSuchElementException(NoSuchElementException e) {
        return new ErrorResult(HttpStatus.BAD_REQUEST.value(), e.getMessage());
    }
}
