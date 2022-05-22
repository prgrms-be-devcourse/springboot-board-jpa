package com.kdt.board.global.config;

import com.kdt.board.domain.controller.api.ApiResponse;
import com.kdt.board.global.exception.NotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    protected ApiResponse<?> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        log.error("MethodArgumentNotValidException", e);

        return ApiResponse.fail(HttpStatus.BAD_REQUEST.value(), e);
    }


    @ExceptionHandler(NotFoundException.class)
    protected ApiResponse<?> handleEntityNotFoundException(NotFoundException e) {
        log.error("NotFoundException", e);

        return ApiResponse.fail(HttpStatus.NOT_FOUND.value(), e);
    }
}
