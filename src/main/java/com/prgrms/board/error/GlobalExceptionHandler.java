package com.prgrms.board.error;

import com.prgrms.board.error.exception.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<GlobalErrorResponse> notFoundException(Exception ex){
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(GlobalErrorResponse.builder()
                        .message(ex.getMessage())
                        .httpStatus(HttpStatus.NOT_FOUND)
                        .build());
    }
}
