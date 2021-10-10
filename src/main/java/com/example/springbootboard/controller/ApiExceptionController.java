package com.example.springbootboard.controller;

import com.example.springbootboard.dto.ResponseError;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ApiExceptionController {

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ResponseError> argumentExceptionHandler(IllegalArgumentException e) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(ResponseError.builder()
                        .errorMessage(HttpStatus.NOT_FOUND.getReasonPhrase())
                        .detailMessage(e.getMessage())
                        .build());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ResponseError> argumentExceptionHandler(Exception e) {
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ResponseError.builder()
                        .errorMessage(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase())
                        .detailMessage(e.getLocalizedMessage())
                        .build());
    }
}

