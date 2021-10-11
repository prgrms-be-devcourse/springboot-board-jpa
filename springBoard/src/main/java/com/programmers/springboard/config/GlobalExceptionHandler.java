package com.programmers.springboard.config;

import javassist.NotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    /*
     * 400 BAD_REQUEST: 잘못된 요청
     */
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity IllegalArgumentHandler(IllegalArgumentException e) {
        log.error("illegalArgumentHandler : {}", e.getMessage());
        return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
    }

    /*
     * 404 NOT_FOUND: 리소스를 찾을 수 없음
     */
    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity notFoundHandler(NotFoundException e) {
        log.error("notFoundHandler : {}", e.getMessage());
        return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
    }

    /*
     * 500 INTERNAL_SERVER_ERROR: 내부 서버 오류
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity internalServerErrorHandler(Exception e) {
        log.error("internalServerErrorHandler : {}", e.getMessage());
        return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
