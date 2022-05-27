package com.example.demo;

import com.example.demo.controller.PostController;
import org.apache.ibatis.javassist.NotFoundException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice(basePackageClasses = PostController.class)
public class PostExceptionHandler {

    @ExceptionHandler(NotFoundException.class)
    public ApiResponse<String> notFoundHandler(NotFoundException e) {
        return ApiResponse.fail(404, e.getMessage());
    }

    @ExceptionHandler
    public ApiResponse<String> errorHandler(Exception e) {
        return ApiResponse.fail(404, e.getMessage());
    }

}
