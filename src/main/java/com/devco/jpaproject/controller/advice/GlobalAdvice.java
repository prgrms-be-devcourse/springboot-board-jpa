package com.devco.jpaproject.controller.advice;

import com.devco.jpaproject.controller.ApiResponse;
import javassist.NotFoundException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalAdvice {

    @ExceptionHandler(NotFoundException.class)
    public ApiResponse<String> notFoundHandler(NotFoundException e){
        return ApiResponse.fail(404, e.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public ApiResponse<String> EnternalServerErrorHandler(Exception e){
        return ApiResponse.fail(500, e.getMessage());
    }

    //TODO: 가능한 모든 예외 처리 추가
}
