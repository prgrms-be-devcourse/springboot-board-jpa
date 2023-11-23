package org.programmers.dev.exception;

import org.programmers.dev.exception.dto.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({
        PostValidationException.class,
        PostNotFoundException.class
    })
    public ResponseEntity<ErrorResponse> handle400Exception(Exception e) {
        return ResponseEntity.badRequest()
            .body(new ErrorResponse(HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.name(),
                e.getMessage()));

    }

}
