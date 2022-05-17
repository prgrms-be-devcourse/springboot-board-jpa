package com.blessing333.boardapi.controller;

import com.blessing333.boardapi.controller.dto.ErrorResult;
import com.blessing333.boardapi.entity.exception.DomainException;
import com.blessing333.boardapi.service.post.PostNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice("com.blessing333.boardapi.controller")
@Slf4j
public class PostApiExceptionHandler {

    @ExceptionHandler(DomainException.class)
    public ResponseEntity<ErrorResult> handleIllegalArgumentException(DomainException e) {
        log.error("[PostApiExceptionHandler]", e);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResult(e.getMessage()));
    }

    @ExceptionHandler(PostNotFoundException.class)
    public ResponseEntity<ErrorResult> handlePostNotFoundException(PostNotFoundException e) {
        log.error("[PostApiExceptionHandler]", e);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResult(e.getMessage()));
    }

}
