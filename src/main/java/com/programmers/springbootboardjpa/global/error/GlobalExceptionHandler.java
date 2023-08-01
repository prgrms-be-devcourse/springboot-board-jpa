package com.programmers.springbootboardjpa.global.error;

import com.programmers.springbootboardjpa.global.common.ApiResponse;
import com.programmers.springbootboardjpa.global.error.exception.InvalidEntityValueException;
import com.programmers.springbootboardjpa.global.error.exception.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(InvalidEntityValueException.class)
    private ApiResponse<String> invalidEntityValueExceptionHandler(InvalidEntityValueException exception) {
        return ApiResponse.fail(HttpStatus.BAD_REQUEST, exception.getMessage());
    }

    @ExceptionHandler(NotFoundException.class)
    private ApiResponse<String> notFoundExceptionHandler(NotFoundException exception) {
        return ApiResponse.fail(HttpStatus.NOT_FOUND, exception.getMessage());
    }

    @ExceptionHandler(Exception.class)
    private ApiResponse<String> exceptionHandler(Exception exception) {
        return ApiResponse.fail(HttpStatus.INTERNAL_SERVER_ERROR, exception.getMessage());
    }
}