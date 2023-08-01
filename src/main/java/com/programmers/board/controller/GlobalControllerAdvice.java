package com.programmers.board.controller;

import org.springframework.http.HttpStatus;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.NoSuchElementException;

@RestControllerAdvice
public class GlobalControllerAdvice {
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(NoSuchElementException.class)
    public ErrorResult noSuchElementExHandle(NoSuchElementException ex) {
        return new ErrorResult(HttpStatus.BAD_REQUEST.value(), ex.getMessage());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ErrorResult methodArgumentNotValidExHandle(MethodArgumentNotValidException ex) {
        String errorMessages = createErrorMessages(ex);
        return new ErrorResult(HttpStatus.BAD_REQUEST.value(), errorMessages);
    }

    private String createErrorMessages(MethodArgumentNotValidException ex) {
        StringBuilder sb = new StringBuilder();
        for (ObjectError error : ex.getAllErrors()) {
            String errorMessage = error.getDefaultMessage();
            sb.append(errorMessage).append(",");
        }
        return sb.toString();
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    public ErrorResult exHandle(Exception ex) {
        String errorMessage = "Unexpected Error occurred";
        return new ErrorResult(HttpStatus.INTERNAL_SERVER_ERROR.value(), errorMessage);
    }
}
