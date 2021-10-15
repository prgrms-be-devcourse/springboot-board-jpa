package com.kdt.api;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice(basePackages = "com.kdt.api")
@ResponseStatus(BAD_REQUEST)
public class ApiExceptionAdvice {

    @ExceptionHandler(Exception.class)
    public ErrorResponse globalException(Exception e) {
        log.error("Unexpected Exception : {}", e.getMessage());
        return ErrorResponse.of(e.getMessage());
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ErrorResponse badRequestException(IllegalArgumentException e) {
        log.error("Unexpected Exception : {}", e.getMessage());
        return ErrorResponse.of(e.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ErrorResponse bindException(MethodArgumentNotValidException e) {
        log.error("Unexpected Exception : {}", e.getMessage());
        return ErrorResponse.of("invalid value", e.getBindingResult());
    }

}
