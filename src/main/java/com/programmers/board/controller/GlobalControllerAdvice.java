package com.programmers.board.controller;

import com.programmers.board.controller.response.ErrorResult;
import com.programmers.board.exception.AuthenticationException;
import com.programmers.board.exception.AuthorizationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DuplicateKeyException;
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
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NoSuchElementException.class)
    public ErrorResult noSuchElementExHandle(NoSuchElementException ex) {
        logWarn(ex);
        return new ErrorResult(ex.getMessage());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ErrorResult methodArgumentNotValidExHandle(MethodArgumentNotValidException ex) {
        String errorMessages = createErrorMessages(ex);
        return new ErrorResult(errorMessages);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(IllegalArgumentException.class)
    public ErrorResult illegalArgumentExHandle(IllegalArgumentException ex) {
        logWarn(ex);
        return new ErrorResult(ex.getMessage());
    }

    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(AuthenticationException.class)
    public ErrorResult authenticationExHandle(AuthenticationException ex) {
        logWarn(ex);
        return new ErrorResult(ex.getMessage());
    }

    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ExceptionHandler(AuthorizationException.class)
    public ErrorResult authorizationExHandle(AuthorizationException ex) {
        logWarn(ex);
        return new ErrorResult(ex.getMessage());
    }

    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler(DuplicateKeyException.class)
    public ErrorResult duplicateKeyExHandle(DuplicateKeyException ex) {
        return new ErrorResult(ex.getMessage());
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    public ErrorResult exHandle(Exception ex) {
        logError(ex);
        return new ErrorResult("서버 오류가 발생했습니다");
    }

    private String createErrorMessages(MethodArgumentNotValidException ex) {
        StringBuilder sb = new StringBuilder();
        for (ObjectError error : ex.getAllErrors()) {
            String errorMessage = error.getDefaultMessage();
            sb.append(errorMessage).append(",");
        }
        return sb.toString();
    }

    private void logWarn(Exception ex) {
        log.warn("[EX TYPE] - {}", ex.getClass().getSimpleName());
        log.warn("[EX MESSAGE] - {}", ex.getMessage());
    }

    private void logError(Exception ex) {
        log.error("[EX]", ex);
    }
}
