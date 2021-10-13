package com.eunu.springbootboard.controller;

import com.eunu.springbootboard.ApiResponse;
import javassist.NotFoundException;
import org.springframework.web.bind.annotation.ExceptionHandler;

public class ExceptionController {
    @ExceptionHandler(NotFoundException.class)
    public ApiResponse<String> notFoundHandler (NotFoundException e) {
        return ApiResponse.fail(404, e.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public ApiResponse<String> internalServerErrorHandler (NotFoundException e) {
        return ApiResponse.fail(500, e.getMessage());
    }
}
