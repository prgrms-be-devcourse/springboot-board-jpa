package com.kdt.Board;

import com.kdt.Board.response.ApiResponse;
import javassist.NotFoundException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.naming.AuthenticationException;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(NotFoundException.class)
    public ApiResponse notFoundHandler (NotFoundException e) {
        return ApiResponse.fail(404, "존재하지 않습니다.", e.getMessage());
    }

    @ExceptionHandler(AuthenticationException.class)
    public ApiResponse notAuthenticatedHandler (AuthenticationException e) {
        return ApiResponse.fail(403, "권한이 없습니다.", e.getMessage());
    }
}
