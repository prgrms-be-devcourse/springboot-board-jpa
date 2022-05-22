package com.dojinyou.devcourse.boardjpa.common.advice;

import com.dojinyou.devcourse.boardjpa.common.exception.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class DefaultControllerAdvice {

    @ExceptionHandler
    public ResponseEntity notFoundExceptionHandler(NotFoundException e) {
        return new ResponseEntity<>(e, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    public ResponseEntity IllegalArgumentExceptionHandler(IllegalArgumentException e) {
        return new ResponseEntity<>(e, HttpStatus.BAD_REQUEST);
    }
}
