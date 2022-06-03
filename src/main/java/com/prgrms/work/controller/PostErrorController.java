package com.prgrms.work.controller;

import com.prgrms.work.controller.dto.ApiResponse;
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
    public ApiResponse<String> postNotFoundException(Exception e) {
        return ApiResponse.fail("해당 게시글을 찾을 수 없습니다.", HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiResponse<String> dtoValidException(Exception e) {
        return ApiResponse.fail(e.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiResponse<String> domainValidException(Exception e) {
        return ApiResponse.fail(e.getMessage(), HttpStatus.BAD_REQUEST);
    }

}
