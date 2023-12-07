package com.prgrms.dev.springbootboardjpa.exception;

import com.prgrms.dev.springbootboardjpa.controller.PostController;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice(basePackageClasses = PostController.class)
public class GlobalExceptionHandler {
    private static final int BAD_REQUEST = 400;
    private static final int NOT_FOUND = 404;
    private static final int INTERNAL_SERVER_ERROR = 500;

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> internalServerErrorHandler(IllegalArgumentException e) {
        return ResponseEntity.status(BAD_REQUEST).body(e.getMessage());
    }

    @ExceptionHandler({PostNotFoundException.class, UserNotFoundException.class})
    public ResponseEntity<String> NotFoundHandler(RuntimeException e) {
        return ResponseEntity.status(NOT_FOUND).body(e.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> internalServerErrorHandler(Exception e) {
        return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(e.getMessage());
    }

}
