package com.example.exception;

import com.example.board.response.Response;
import com.fasterxml.jackson.databind.exc.MismatchedInputException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BaseException.class)
    public Response<?> customExceptionHandle(BaseException e){
        return Response.fail(e);
    }

    @ExceptionHandler(RuntimeException.class)
    public Response<?> customExceptionHandle(RuntimeException e){
        return Response.fail(e);
    }

    @ExceptionHandler(Exception.class)
    public Response<?> customExceptionHandle(Exception e){
        return Response.fail(e);
    }

    @ExceptionHandler(MismatchedInputException.class)
    public Response<?> customExceptionHandle(MismatchedInputException e){
        return Response.fail(e);
    }
}
