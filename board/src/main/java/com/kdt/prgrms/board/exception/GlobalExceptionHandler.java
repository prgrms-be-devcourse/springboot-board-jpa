package com.kdt.prgrms.board.exception;

import com.kdt.prgrms.board.exception.custom.AccessDeniedException;
import com.kdt.prgrms.board.exception.custom.NotFoundException;
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

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleMethodArgumentValidException() {

        logger.error("MethodArgumentValidException : {}", INVALID_INPUT_REQUEST.getMessage());

        return ErrorResponse.toResponseEntity(INVALID_INPUT_REQUEST);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<?> handleIllegalArgumentException() {

        logger.error("IllegalArgumentException : {}", INVALID_INPUT_REQUEST.getMessage());

        return ErrorResponse.toResponseEntity(INVALID_INPUT_REQUEST);
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ErrorResponse> handleNotFoundException() {

        logger.error("NotFoundException : {}", REQUEST_NOT_FOUND.getMessage());

        return ErrorResponse.toResponseEntity(REQUEST_NOT_FOUND);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ErrorResponse> handleAccessDeniedException() {

        logger.error("AccessDeniedException : {}", ACCESS_DENIED.getMessage());

        return ErrorResponse.toResponseEntity(ACCESS_DENIED);
    }
}
