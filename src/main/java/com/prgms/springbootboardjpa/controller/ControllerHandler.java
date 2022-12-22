package com.prgms.springbootboardjpa.controller;

import com.prgms.springbootboardjpa.dto.ApiErrorResponse;
import com.prgms.springbootboardjpa.exception.MemberNotFoundException;
import com.prgms.springbootboardjpa.exception.PostNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ControllerHandler {
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(MemberNotFoundException.class)
    public ApiErrorResponse memberNotFoundHandle(MemberNotFoundException e) {
        return new ApiErrorResponse(e.getMessage());
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(PostNotFoundException.class)
    public ApiErrorResponse postNotFoundHandle(PostNotFoundException e) {
        return new ApiErrorResponse(e.getMessage());
    }
}