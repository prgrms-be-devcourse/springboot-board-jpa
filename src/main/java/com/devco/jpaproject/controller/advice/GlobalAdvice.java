package com.devco.jpaproject.controller.advice;

import com.devco.jpaproject.controller.ApiResponse;
import com.devco.jpaproject.exception.PostNotFoundException;
import com.devco.jpaproject.exception.UserAndPostNotMatchException;
import com.devco.jpaproject.exception.UserNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalAdvice {

    @ExceptionHandler(PostNotFoundException.class)
    public ApiResponse<String> postNotFoundHandler(PostNotFoundException e){
        return ApiResponse.fail(HttpStatus.NOT_FOUND.value(), e.getMessage());
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ApiResponse<String> userNotFoundHandler(UserNotFoundException e){
        return ApiResponse.fail(HttpStatus.NOT_FOUND.value(), e.getMessage());
    }

    @ExceptionHandler(UserAndPostNotMatchException.class)
    public ApiResponse<String> userAndPostNotMatchHandler(UserAndPostNotMatchException e){
        return ApiResponse.fail(HttpStatus.NOT_FOUND.value(), e.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public ApiResponse<String> enternalServerErrorHandler(Exception e){
        return ApiResponse.fail(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage());
    }

    //TODO: 가능한 모든 예외 처리 추가
}
