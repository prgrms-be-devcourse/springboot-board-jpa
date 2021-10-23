package com.prgrms.board.error;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ConstraintViolationException;

@RestControllerAdvice
public class InvalidExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    protected ResponseEntity<InvalidErrorResponse> argumentNotValidException(MethodArgumentNotValidException ex) {
        FieldError error = ex.getBindingResult().getFieldErrors().get(0);

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(
                        InvalidErrorResponse.builder()
                        .field(error.getField())
                        .message(error.getDefaultMessage())
                        .build()
                );
    }

    @ExceptionHandler(ConstraintViolationException.class)
    protected ResponseEntity<InvalidErrorResponse> constraintViolationException(ConstraintViolationException ex) {
        String[] error = ex.getLocalizedMessage().split(":");

        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(
                        InvalidErrorResponse.builder()
                        .field(error[0])
                        .message(error[1])
                        .build()
                );
    }

}
