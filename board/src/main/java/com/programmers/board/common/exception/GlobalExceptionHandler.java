package com.programmers.board.common.exception;

import com.programmers.board.api.common.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import javax.persistence.EntityNotFoundException;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value = Exception.class)
    public ErrorResponse handleServerException(Exception e){
        log.error(e.getMessage(), e);
        return ErrorResponse.of(ErrorCode.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(value = HttpRequestMethodNotSupportedException.class)
    public ErrorResponse handleHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException e){
        log.error(e.getMessage(), e);
        return ErrorResponse.of(ErrorCode.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public ErrorResponse handleMethodArgumentNotValidException (MethodArgumentNotValidException e){
        log.error(e.getMessage(), e);
        return ErrorResponse.of(ErrorCode.INVALID_INPUT_VALUE);
    }

    @ExceptionHandler(value = MethodArgumentTypeMismatchException.class)
    public ErrorResponse handleMethodArgumentNotValidException (MethodArgumentTypeMismatchException e){
        log.error(e.getMessage(), e);
        return ErrorResponse.of(ErrorCode.INVALID_TYPE_VALUE);
    }

    @ExceptionHandler(value = EntityNotFoundException.class)
    public ErrorResponse handleEntityNotFoundException(EntityNotFoundException e){
        log.error(e.getMessage(), e);
        return ErrorResponse.of(ErrorCode.POST_NOT_FOUND);
    }

}
