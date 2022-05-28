package com.prgrms.springbootboardjpa.exception.controller;

import com.prgrms.springbootboardjpa.exception.exceptions.CustomRuntimeException;
import com.prgrms.springbootboardjpa.exception.response.ErrorResponse;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionRestControllerAdvice {

    @ExceptionHandler(IllegalArgumentException.class)
    public ErrorResponse illegalArgumentHandler(IllegalArgumentException e){
        return ErrorResponse.IllegalArgumentException(e.getMessage());
    }

    @ExceptionHandler(CustomRuntimeException.class)
    public ErrorResponse customRuntimeHandler(CustomRuntimeException e){
        return ErrorResponse.customRuntimeException(e.getExceptionCode().getStatus(), e.getExceptionCode().getErrorMessage());
    }
}
