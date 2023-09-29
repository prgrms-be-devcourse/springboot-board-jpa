package com.blackdog.springbootBoardJpa.global.exception;

import com.blackdog.springbootBoardJpa.global.response.ErrorCode;
import com.blackdog.springbootBoardJpa.global.response.ErrorResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static com.blackdog.springbootBoardJpa.global.response.ErrorCode.*;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private final Logger log = LoggerFactory.getLogger(getClass());

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> methodArgumentNotValidExceptionHandler(MethodArgumentNotValidException e) {
        ErrorResponse errorResponse = ErrorResponse.of(INVALID_METHOD_ERROR);
        log.warn("{}", errorResponse);
        log.warn("{}", e.getCause());
        e.printStackTrace();
        return ResponseEntity
                .status(HttpStatus.METHOD_NOT_ALLOWED)
                .body(errorResponse);
    }

    @ExceptionHandler(PermissionDeniedException.class)
    public ResponseEntity<ErrorResponse> permissionDeniedExceptionHandler(PermissionDeniedException e) {
        ErrorResponse errorResponse = ErrorResponse.of(ErrorCode.PERMISSION_DENIED);
        log.warn("{}", errorResponse);
        log.warn("{}", e.getCause());
        return ResponseEntity
                .status(HttpStatus.FORBIDDEN)
                .body(errorResponse);
    }

    @ExceptionHandler(PostNotFoundException.class)
    public ResponseEntity<ErrorResponse> postNotFoundExceptionHandler(PostNotFoundException e) {
        ErrorResponse errorResponse = ErrorResponse.of(NOT_FOUND_POST);
        log.warn("{}", errorResponse);
        log.warn("{}", e.getCause());
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(errorResponse);
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ErrorResponse> userNotFoundExceptionHandler(UserNotFoundException e) {
        ErrorResponse errorResponse = ErrorResponse.of(NOT_FOUND_USER);
        log.warn("{}", errorResponse);
        log.warn("{}", e.getCause());
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(errorResponse);
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ErrorResponse> handleRuntimeException(RuntimeException e) {
        ErrorResponse errorResponse = ErrorResponse.of(INTERNAL_SERVER_ERROR);
        log.warn("{}", errorResponse);
        log.warn("{}", e.getCause());
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(errorResponse);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleAllException(Exception e) {
        ErrorResponse errorResponse = ErrorResponse.of(INTERNAL_SERVER_ERROR);
        log.warn("{}", errorResponse);
        log.warn("{}", e.getCause());
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(errorResponse);
    }

}
