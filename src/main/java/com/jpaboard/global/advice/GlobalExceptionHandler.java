package com.jpaboard.global.advice;

import com.jpaboard.global.exception.PostNotFoundException;
import com.jpaboard.global.exception.UserNotFoundException;
import com.jpaboard.global.exception.response.ErrorCodeWithDetail;
import com.jpaboard.global.exception.response.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResponse handleException(Exception e) {
        return ErrorResponse.create(ErrorCodeWithDetail.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler({PostNotFoundException.class, UserNotFoundException.class})
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handleEntityNotFoundException(RuntimeException e) {
        return ErrorResponse.create(ErrorCodeWithDetail.ENTITY_NOT_FOUND);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        return ErrorResponse.create(ErrorCodeWithDetail.BAD_REQUEST, e.getBindingResult());
    }

}
