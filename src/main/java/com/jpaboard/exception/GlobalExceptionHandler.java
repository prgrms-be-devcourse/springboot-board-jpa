package com.jpaboard.exception;

import com.jpaboard.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiResponse<String> invalidHandle(IllegalArgumentException exception) {
        return ApiResponse.fail(HttpStatus.BAD_REQUEST.value(), exception.getMessage());
    }

    @ExceptionHandler(NotFoundPostException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ApiResponse<String> notFoundUserHandle(NotFoundPostException notFoundPostException) {
        return ApiResponse.fail(HttpStatus.NOT_FOUND.value(), notFoundPostException.getMessage());
    }

    @ExceptionHandler(NotFoundUserException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ApiResponse<String> notFoundUserHandle(NotFoundUserException notFoundUserException) {
        return ApiResponse.fail(HttpStatus.NOT_FOUND.value(), notFoundUserException.getMessage());
    }
}
