package com.devco.jpaproject.controller.advice;

import com.devco.jpaproject.controller.ApiResponse;
import com.devco.jpaproject.exception.PostNotFoundException;
import com.devco.jpaproject.exception.UserAndPostNotMatchException;
import com.devco.jpaproject.exception.UserNotFoundException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalAdvice {

    @ExceptionHandler(PostNotFoundException.class)
    public ApiResponse<String> postNotFoundHandler(PostNotFoundException e){
        return ApiResponse.fail(404, e.getMessage());
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ApiResponse<String> userNotFoundHandler(UserNotFoundException e){
        return ApiResponse.fail(404, e.getMessage());
    }

    @ExceptionHandler(UserAndPostNotMatchException.class)
    public ApiResponse<String> UserAndPostNotMatchHandler(UserAndPostNotMatchException e){
        return ApiResponse.fail(404, e.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public ApiResponse<String> EnternalServerErrorHandler(Exception e){
        return ApiResponse.fail(500, e.getMessage());
    }

    //TODO: 가능한 모든 예외 처리 추가
}
