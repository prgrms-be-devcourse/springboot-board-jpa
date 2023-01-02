package com.programmers.kwonjoosung.board.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler { // 오류 메시지를 어떻게 일관성을 가지고 처리할 것인가?

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> illegalArgumentExceptionHandle(IllegalArgumentException e) {
        log.error("IllegalArgumentException: {}", e.getMessage(),e);
        return ResponseEntity.badRequest().body(e.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> exceptionHandle(Exception e) {
        log.error("Exception: {}", e.getMessage(),e);
        return ResponseEntity.badRequest().body(e.getMessage());
    }

}
