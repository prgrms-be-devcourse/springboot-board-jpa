package com.programmers.board.controller;

import com.programmers.board.controller.response.ErrorResult;
import com.programmers.board.exception.AuthorizationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.NoSuchElementException;

@Slf4j
@RestControllerAdvice
public class GlobalControllerAdvice {
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(NoSuchElementException.class)
    public ErrorResult noSuchElementExHandle(NoSuchElementException ex) {
        return new ErrorResult(ex.getMessage());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ErrorResult methodArgumentNotValidExHandle(MethodArgumentNotValidException ex) {
        String errorMessages = createErrorMessages(ex);
        return new ErrorResult(errorMessages);
    }

    private String createErrorMessages(MethodArgumentNotValidException ex) {
        StringBuilder sb = new StringBuilder();
        for (ObjectError error : ex.getAllErrors()) {
            String errorMessage = error.getDefaultMessage();
            sb.append(errorMessage).append(",");
        }
        return sb.toString();
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(IllegalArgumentException.class)
    public ErrorResult illegalArgumentExHandle(IllegalArgumentException ex) {
        return new ErrorResult(ex.getMessage());
    }

    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ExceptionHandler(AuthorizationException.class)
    public ErrorResult authorizationExHandle(AuthorizationException ex) {
        log.warn(ex.getMessage(), ex);
        return new ErrorResult(ex.getMessage());
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    public ErrorResult exHandle(Exception ex) {
        String errorMessage = "Unexpected Error occurred";
        return new ErrorResult(errorMessage);
    }
}
