package com.hyunji.jpaboard.web.controller;

import com.hyunji.jpaboard.web.dto.ErrorResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ConstraintViolationException;

@Slf4j
@RestControllerAdvice
public class GlobalErrorHandler {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({
            IllegalArgumentException.class,
            IllegalStateException.class,
            ConstraintViolationException.class,
            MethodArgumentNotValidException.class,
            HttpMessageNotReadableException.class
    })
    public ErrorResult handleBadRequestException(Exception e) {
        log.info("Bad request exception occurred: {}", e.getMessage(), e);
        return new ErrorResult("BAD_REQUEST", "잘못된 요청입니다.");
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler
    public ErrorResult exHandle(Exception e) {
        log.error("Unexpected exception occurred: {}", e.getMessage(), e);
        return new ErrorResult("INTERNAL_SERVER_ERROR", "내부 오류가 발생했습니다.");
    }
}
