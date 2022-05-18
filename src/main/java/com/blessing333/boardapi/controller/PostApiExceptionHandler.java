package com.blessing333.boardapi.controller;

import com.blessing333.boardapi.controller.dto.ErrorResult;
import com.blessing333.boardapi.entity.exception.DomainException;
import com.blessing333.boardapi.entity.exception.PostCreateFailException;
import com.blessing333.boardapi.entity.exception.PostUpdateFailException;
import com.blessing333.boardapi.service.post.PostNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice("com.blessing333.boardapi.controller")
@Slf4j
public class PostApiExceptionHandler {
    private static final String HANDLER_NAME = "[PostApiExceptionHandler]";

    @ExceptionHandler(DomainException.class)
    public ResponseEntity<ErrorResult> handleDomainException(DomainException e) {
        log.error(HANDLER_NAME, e);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResult(e.getMessage()));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResult> handleBeanValidationException(MethodArgumentNotValidException e) {
        BindingResult bindingResult = e.getBindingResult();
        log.error(HANDLER_NAME, e);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ErrorResult(bindingResult.getFieldError().getDefaultMessage()));
    }

    @ExceptionHandler(PostNotFoundException.class)
    public ResponseEntity<ErrorResult> handlePostNotFoundException(PostNotFoundException e) {
        log.error(HANDLER_NAME, e);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResult(e.getMessage()));
    }

    @ExceptionHandler(PostCreateFailException.class)
    public ResponseEntity<ErrorResult> handlePostCreateFailException(PostCreateFailException e) {
        log.error(HANDLER_NAME, e);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResult(e.getMessage()));
    }

    @ExceptionHandler(PostUpdateFailException.class)
    public ResponseEntity<ErrorResult> handlePostUpdateFailException(PostUpdateFailException e) {
        log.error(HANDLER_NAME, e);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResult(e.getMessage()));
    }

}
