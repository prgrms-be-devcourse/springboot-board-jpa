package com.example.demo.exception;

import com.example.demo.controller.PostController;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;


@Slf4j
@RestControllerAdvice(basePackageClasses = PostController.class)
public class PostExceptionHandler {

    @ExceptionHandler(PostNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleNotFoundException (PostNotFoundException e) {
        log.error("PostNotFoundException", e);
        ErrorResponse response = new ErrorResponse(ErrorStatus.ENTITY_NOT_FOUND);
        return new ResponseEntity<>(response, HttpStatus.NO_CONTENT);
    }

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handleException (Exception e) {
        log.error("handleException", e);
        ErrorResponse response = new ErrorResponse(ErrorStatus.INTERNAL_SERVER_ERROR);
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
