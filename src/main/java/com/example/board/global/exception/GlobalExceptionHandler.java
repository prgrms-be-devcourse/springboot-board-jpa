package com.example.board.global.exception;

import com.example.board.global.dto.BindingErrorResponse;
import com.example.board.global.dto.InnerErrorResponse;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Objects;

import static org.springframework.http.HttpStatus.*;

@Slf4j
@RestControllerAdvice
@RequiredArgsConstructor
public class GlobalExceptionHandler {

    @ResponseStatus(BAD_REQUEST)
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public BindingErrorResponse httpMessageBindEx(BindException bindException) {
        log.warn("[HttpMessageNotReadableException] - {} ", bindException.getFieldError());
        return BindingErrorResponse.of(Objects.requireNonNull(bindException.getFieldError()));
    }

    @ResponseStatus(BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public BindingErrorResponse methodArgBindEx(BindException bindException) {
        log.warn("[MethodArgumentNotValidException] - {} ", bindException.getFieldError());
        return BindingErrorResponse.of(Objects.requireNonNull(bindException.getFieldError()));
    }

    @ResponseStatus(NOT_FOUND)
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public BindingErrorResponse httpReqMethodBindEx(BindException bindException) {
        log.warn("[HttpRequestMethodNotSupportedException] - {} ", bindException.getFieldError());
        return BindingErrorResponse.of(Objects.requireNonNull(bindException.getFieldError()));
    }

    @ResponseStatus(NOT_FOUND)
    @ExceptionHandler(EntityNotFoundException.class)
    public BindingErrorResponse entityNotFoundBindEx(BindException bindException) {
        log.warn("[EntityNotFoundException] - {} ", bindException.getFieldError());
        return BindingErrorResponse.of(Objects.requireNonNull(bindException.getFieldError()));
    }

    @ResponseStatus(BAD_REQUEST)
    @ExceptionHandler(IllegalArgumentException.class)
    public InnerErrorResponse illegalArgEx(IllegalArgumentException e) {
        log.error("[IllegalArgumentException] - {}", e.getMessage());
        return InnerErrorResponse.of(BAD_REQUEST.name(), e.getMessage());
    }

    @ResponseStatus(INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    public InnerErrorResponse exceptionEx(Exception e) {
        log.error("[Exception] - {} ", e.getMessage());
        return InnerErrorResponse.of(INTERNAL_SERVER_ERROR.name(), e.getMessage());
    }
}
