package com.kdt.springbootboardjpa.global;

import com.kdt.springbootboardjpa.global.exception.NotFoundEntityException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class PostControllerAdvice {

    @ExceptionHandler(NotFoundEntityException.class)
    public ResponseEntity NotFoundEntityHandler(NotFoundEntityException e) {
        log.error("{}", e.getMessage());
        return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity NotFoundExceptionHandler(Exception e) {
        log.error("{}", e.getMessage());
        return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
