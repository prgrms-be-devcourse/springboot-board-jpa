package com.example.board.api;


import javassist.NotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class ApiException {

    @ExceptionHandler(NotFoundException.class)
    public ApiResponse<String> notfound(NotFoundException e){return ApiResponse.fail(404,e.getMessage());}

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ApiResponse<String> argNotValid(MethodArgumentNotValidException e){return ApiResponse.fail(400,e.getMessage());}


    @ExceptionHandler(Exception.class)
    public ApiResponse<String> notfound(Exception e){return ApiResponse.fail(500,e.getMessage());}


}
