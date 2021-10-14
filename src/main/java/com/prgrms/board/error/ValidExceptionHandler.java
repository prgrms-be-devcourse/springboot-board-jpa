package com.prgrms.board.error;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ValidExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    protected ResponseEntity<ValidErrorResponse> argumentNotValidException(MethodArgumentNotValidException ex) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(ValidErrorResponse.builder()
                        .field(ex.getBindingResult().getFieldErrors().get(0).getField())
                        .message(ex.getBindingResult().getFieldErrors().get(0).getDefaultMessage())
                        .build());
    }

}
