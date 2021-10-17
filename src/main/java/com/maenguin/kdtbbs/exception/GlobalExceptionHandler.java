package com.maenguin.kdtbbs.exception;


import com.maenguin.kdtbbs.dto.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Arrays;
import java.util.Optional;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    private ResponseEntity<ApiResponse<?>> newResponseEntity(Throwable throwable, HttpStatus status) {
        Optional<String> message = Arrays.stream(throwable.getStackTrace()).map(StackTraceElement::toString).reduce((a, b) -> a + " " + b);
        return newResponseEntity(ErrorCode.UNHANDLED.getCode(), message.orElse(throwable.getMessage()), status);
    }

    private ResponseEntity<ApiResponse<?>> newResponseEntity(int errorCode, String message, HttpStatus status) {
        return new ResponseEntity<>(ApiResponse.error(errorCode, message), status);
    }

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<?> handleBusinessException(BusinessException e) {
        log.error("businessException exception occurred: {}", e.getMessage(), e);
        return newResponseEntity(e.getErrorCode().getCode(), e.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({Exception.class, RuntimeException.class})
    public ResponseEntity<?> handleException(Exception e) {
        log.error("Unexpected exception occurred: {}", e.getMessage(), e);
        return newResponseEntity(e, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
