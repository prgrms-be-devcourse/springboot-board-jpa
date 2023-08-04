package com.kdt.devcourse.global.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@RestControllerAdvice
public class GlobalExceptionHandler {
    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    private static final String INTERNAL_ERROR_MESSAGE = "서버 내부 오류가 발생했습니다.";

    @ExceptionHandler(PostNotFoundException.class)
    @ResponseStatus(NOT_FOUND)
    public ErrorResponse handleEntityNotFoundException(PostNotFoundException e) {
        log.warn("PostNotFoundException : {}", e.getMessage());
        return new ErrorResponse(e.getErrorCode());
    }

    @ExceptionHandler(RuntimeException.class)
    @ResponseStatus(INTERNAL_SERVER_ERROR)
    public ErrorResponse handleUnexpectedException(RuntimeException e) {
        log.warn("UnexpectedException Occures : {}", e.getMessage());
        return new ErrorResponse(INTERNAL_ERROR_MESSAGE);
    }
}
