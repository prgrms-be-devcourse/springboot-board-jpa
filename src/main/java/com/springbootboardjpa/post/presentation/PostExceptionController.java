package com.springbootboardjpa.post.presentation;

import com.springbootboardjpa.common.NoSuchEntityException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class PostExceptionController {

    @ExceptionHandler(NoSuchEntityException.class)
    public ResponseEntity<String> handleNoSuchEntity(NoSuchEntityException e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }
}
