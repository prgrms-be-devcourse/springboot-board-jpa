package com.assignment.bulletinboard.api;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice(basePackages = "com.assignment.bulletinboard.api")
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class ErrorHandler {

    @ExceptionHandler(Exception.class)
    public ErrorResponse generalException (Exception e) {
        log.error("Exception Occurred : {}", e.getMessage());
        return ErrorResponse.of(e.getMessage());
    }

    @ExceptionHandler(RuntimeException.class)
    public ErrorResponse badRequestException (RuntimeException e) {
        log.error("Unexpected Exception occurred : {}", e.getMessage());
        return ErrorResponse.of(e.getMessage());
    }
}
