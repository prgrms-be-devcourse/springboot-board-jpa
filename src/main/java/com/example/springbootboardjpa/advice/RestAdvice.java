package com.example.springbootboardjpa.advice;

import com.example.springbootboardjpa.response.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class RestAdvice {
    @ExceptionHandler(value = {RuntimeException.class})
    public ApiResponse<String> error(Exception e) {
        return ApiResponse.fail(HttpStatus.BAD_REQUEST, e.getMessage());
    }
}
