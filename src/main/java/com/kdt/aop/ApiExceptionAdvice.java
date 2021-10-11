package com.kdt.aop;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.NOT_FOUND;

import com.kdt.exception.NotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice(basePackages = "com.kdt.api")
@Slf4j
public class ApiExceptionAdvice {

    @ExceptionHandler(Exception.class)
    public ResponseEntity globalException(Exception e) {
        log.error("Unexpected Exception : {}", e.getMessage());
        return ResponseEntity.badRequest().body(ErrorResponse.of(e.getMessage()));
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity badRequestException(Exception e) {
        log.error("Unexpected Exception : {}", e.getMessage());
        return ResponseEntity.status(NOT_FOUND).body(ErrorResponse.of(e.getMessage()));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity bindException(MethodArgumentNotValidException e) {
        log.error("Unexpected Exception : {}", e.getMessage());
        return ResponseEntity.status(BAD_REQUEST).body(ErrorResponse.of(e.getBindingResult()));
    }

}
