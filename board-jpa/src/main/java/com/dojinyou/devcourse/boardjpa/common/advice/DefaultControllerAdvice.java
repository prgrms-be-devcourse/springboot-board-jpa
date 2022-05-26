package com.dojinyou.devcourse.boardjpa.common.advice;

import com.dojinyou.devcourse.boardjpa.common.exception.NotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

@RestControllerAdvice
public class DefaultControllerAdvice {

    @ExceptionHandler({NotFoundException.class})
    public ResponseEntity<String> notFoundExceptionHandler() {
        return ResponseEntity.notFound().build();
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity IllegalArgumentExceptionHandler() {
        return ResponseEntity.badRequest().build();
    }

    @ExceptionHandler({MethodArgumentTypeMismatchException.class})
    public ResponseEntity MethodArgumentTypeMismatchExceptionHandler() {
        return ResponseEntity.badRequest().build();
    }

    @ExceptionHandler({RuntimeException.class})
    public ResponseEntity<String> RuntimeExceptionHandler() {
        return ResponseEntity.internalServerError().build();
    }
}
