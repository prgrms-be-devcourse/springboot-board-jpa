package com.prgrms.dev.springbootboardjpa.exception;

import com.prgrms.dev.springbootboardjpa.controller.PostController;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice(basePackageClasses = PostController.class)
public class GlobalExceptionHandler {

    private static final int NOT_FOUND = 404;
    private static final int INTERNAL_SERVER_ERROR = 500;

    @ExceptionHandler(ChangeSetPersister.NotFoundException.class)
    public ResponseEntity<String> notFoundHandler(ChangeSetPersister.NotFoundException e) {
        return ResponseEntity.status(NOT_FOUND).body(e.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> internalServerErrorHandler(Exception e) {
        return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(e.getMessage());
    }
}
