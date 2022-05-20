package com.kdt.prgrms.board.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static com.kdt.prgrms.board.exception.ErrorCode.*;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler({MethodArgumentNotValidException.class, IllegalArgumentException.class})
    public ResponseEntity<?> handleMethodArgumentValidException() {

        logger.error("MethodArgumentValidException : {}", INVALID_INPUT_REQUEST.getMessage());
        ResponseEntity<ErrorResponse> result = ErrorResponse.toResponseEntity(INVALID_INPUT_REQUEST);

        return ErrorResponse.toResponseEntity(INVALID_INPUT_REQUEST);
    }
}
