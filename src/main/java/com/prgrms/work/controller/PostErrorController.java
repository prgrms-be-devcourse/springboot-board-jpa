package com.prgrms.work.controller;

import com.prgrms.work.controller.dto.ApiResponse;
import com.prgrms.work.error.EntityInvalidException;
import com.prgrms.work.error.PostNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class PostErrorController {

    @ExceptionHandler(PostNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ApiResponse<String> postNotFoundException(PostNotFoundException e) {
        return ApiResponse.fail(e.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiResponse<String> dtoValidException(MethodArgumentNotValidException e) {
        return ApiResponse.fail(e.getFieldError().getDefaultMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(EntityInvalidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiResponse<String> domainValidException(EntityInvalidException e) {
        return ApiResponse.fail(e.getMessage(), HttpStatus.BAD_REQUEST);
    }

}
