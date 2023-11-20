package com.example.board.exception;

import com.example.board.dto.response.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ApiResponse<String> handleRuntimeException(Exception e) {
        return ApiResponse.fail(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
    }

}
