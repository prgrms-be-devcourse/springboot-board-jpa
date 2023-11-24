package com.example.board.exception;

import com.example.board.response.Response;
import com.fasterxml.jackson.databind.exc.MismatchedInputException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

// TODO: httpstatus 상태 코드도 함께 전달하는 방법 찾기
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BaseException.class)
    public ResponseEntity<Response<?>> baseExceptionHandle(BaseException e) {
        log.info("error: {}", e.getMessage());
        return ResponseEntity.status(e.getHttpStatus()).body(Response.fail(e));
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<Response<?>> runtimeExceptionHandle(RuntimeException e) {
        log.info("error: {}", e.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Response.fail(e));
    }

    @ExceptionHandler(BindingException.class)
    public ResponseEntity<Response<?>> bindingExceptionHandle(BindingException e) {
        log.info("error: {}", e.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Response.fail(e.getBindingMessages()));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Response<?>> exceptionHandle(Exception e) {
        log.info("error: {}", e.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Response.fail(e));
    }

    @ExceptionHandler(MismatchedInputException.class)
    public ResponseEntity<Response<?>> customExceptionHandle(MismatchedInputException e) {
        log.info("error: {}", e.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Response.fail(e));
    }
}
