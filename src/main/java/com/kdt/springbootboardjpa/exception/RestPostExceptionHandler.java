package com.kdt.springbootboardjpa.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
@ResponseBody
@Slf4j
public class RestPostExceptionHandler {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(UserNotFoundException.class)
    public ErrorResponse handleUnAuthorization(UserNotFoundException e) {
        log.warn(e.getMessage(), e);
        var errorCode = ErrorCode.NOT_AUTHORIZED;
        return ErrorResponse.builder()
                .code(HttpStatus.BAD_REQUEST)
                .message(errorCode.getMessage())
                .status(errorCode.getStatus())
                .timestamp(LocalDateTime.now())
                .build();
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(PostNotFoundException.class)
    public ErrorResponse handleEntityNotFound(PostNotFoundException e) {
        log.warn(e.getMessage(), e);
        var errorCode = ErrorCode.ENTITY_NOT_FOUND;
        return ErrorResponse.builder()
                .code(HttpStatus.NOT_FOUND)
                .message(errorCode.getMessage())
                .status(errorCode.getStatus())
                .timestamp(LocalDateTime.now())
                .build();
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ErrorResponse handleNotValidArgument(MethodArgumentNotValidException e) {
        log.warn("Not valid Argument.", e);
        var errorCode = ErrorCode.NOT_VALID;
        return ErrorResponse.builder()
                .code(HttpStatus.BAD_REQUEST)
                .message(errorCode.getMessage())
                .status(errorCode.getStatus())
                .timestamp(LocalDateTime.now())
                .build();
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(RuntimeException.class)
    public ErrorResponse handleDefaultException(RuntimeException e) {
        log.warn("Default Exception.", e);
        var errorCode = ErrorCode.INTERNAL_SERVER_ERROR;
        return ErrorResponse.builder()
                .code(HttpStatus.INTERNAL_SERVER_ERROR)
                .message(errorCode.getMessage())
                .status(errorCode.getStatus())
                .timestamp(LocalDateTime.now())
                .build();
    }
}
