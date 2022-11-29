package com.springboard.common.aop;

import com.springboard.common.exception.FindFailException;
import org.springframework.dao.DataAccessException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class CommonExceptionHandler {
    @ExceptionHandler(FindFailException.class)
    public ResponseEntity<Object> handleFindFailException() {
        return ResponseEntity.notFound().build();
    }

    @ExceptionHandler(DataAccessException.class)
    public ResponseEntity<Object> handleDataAccessException(Exception e) {
        return ResponseEntity.badRequest().body(new Message(e.getMessage()));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Object> processValidationError(MethodArgumentNotValidException e) {
        return ResponseEntity.badRequest().body(new Message(e.getBindingResult().getAllErrors().get(0).getDefaultMessage()));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleException(Exception e) {
        return ResponseEntity.internalServerError().body(new Message(e.getMessage()));
    }

    record Message(
        String message
    ){}
}
