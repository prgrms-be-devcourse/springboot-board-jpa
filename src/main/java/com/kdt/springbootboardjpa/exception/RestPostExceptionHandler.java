package com.kdt.springbootboardjpa.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.NoSuchElementException;

@RestControllerAdvice
@ResponseBody
@Slf4j
public class RestPostExceptionHandler {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(UnAuthorizationAccessException.class)
    public ErrorResponse handleUnAuthorization(UnAuthorizationAccessException e) {
        log.info(e.getMessage());
        var errorCode = ErrorCode.NOT_AUTHORIZED;
        return ErrorResponse.builder()
                .code(HttpStatus.BAD_REQUEST)
                .message(errorCode.getMessage())
                .status(errorCode.getStatus())
                .timestamp(LocalDateTime.now())
                .build();
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NoSuchElementException.class)
    public ErrorResponse handleEntityNotFound(NoSuchElementException e){
        log.info(e.getMessage());
        var errorCode = ErrorCode.ENTITY_NOT_FOUND;
        return ErrorResponse.builder()
                .code(HttpStatus.NOT_FOUND)
                .message(errorCode.getMessage())
                .status(errorCode.getStatus())
                .timestamp(LocalDateTime.now())
                .build();
    }
}
