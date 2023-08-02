package com.programmers.jpa_board.global.exception;

import com.programmers.jpa_board.global.ApiResponse;
import com.programmers.jpa_board.user.exception.NotFoundUserException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiResponse<String> invalidHandle(IllegalArgumentException exception) {
        return ApiResponse.fail(HttpStatus.BAD_REQUEST, exception.getMessage());
    }

    @ExceptionHandler(NotFoundUserException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ApiResponse<String> invalidHandle(NotFoundUserException exception) {
        return ApiResponse.fail(HttpStatus.NOT_FOUND, exception.getMessage());
    }
}
