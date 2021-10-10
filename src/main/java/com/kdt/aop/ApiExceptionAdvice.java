package com.kdt.aop;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice(basePackages = "com.kdt.api")
@Slf4j
public class ApiExceptionAdvice {

    @ExceptionHandler(Exception.class)
    public ResponseEntity globalException(Exception e) {
        log.error("Unexpected Exception : {}", e.getMessage());
        return ResponseEntity.badRequest().body(ErrorResponse.of("Unknown Exception"));
    }

}
