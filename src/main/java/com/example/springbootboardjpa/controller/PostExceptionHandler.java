package com.example.springbootboardjpa.controller;

import com.example.springbootboardjpa.exception.NotFoundException;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.java.Log;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice(basePackageClasses = PostController.class)
@Log
public class PostExceptionHandler {

    @ExceptionHandler(NotFoundException.class)
    public ApiResponse<String> notFoundHandler(NotFoundException e) {
        log.warning("NotFoundException 발생 : "+e.getMessage());
        return ApiResponse.fail(404, e.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ApiResponse<String> controllerValidationErrorHandler(MethodArgumentNotValidException e) {
        log.warning("MethodArgumentNotValidException 발생 : "+e.getMessage());
        return ApiResponse.fail(400, e.getBindingResult().toString());
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ApiResponse<String> ServiceValidationErrorHandler(ConstraintViolationException e) {
        log.warning("ConstraintViolationException 발생 : "+e.getMessage());
        return ApiResponse.fail(400, e.toString());
    }

    @ExceptionHandler(Exception.class)
    public ApiResponse<String> internalServerErrorHandler(Exception e) {
        e.printStackTrace();
        log.warning("Exception 발생 : "+e.getMessage());
        return ApiResponse.fail(500, e.getMessage());
    }

}
