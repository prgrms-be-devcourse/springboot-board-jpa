package com.maenguin.kdtbbs.exception;


import com.maenguin.kdtbbs.dto.ApiResponse;
import java.util.Arrays;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    private ResponseEntity<ApiResponse<?>> newResponseEntity(Throwable throwable, HttpStatus status) {
        String message = Arrays.stream(throwable.getStackTrace()).map(StackTraceElement::toString)
            .collect(Collectors.joining(", "));
        return newResponseEntity(ErrorCode.UNHANDLED.getCode(), message, status);
    }

    private ResponseEntity<ApiResponse<?>> newResponseEntity(int errorCode, String message, HttpStatus status) {
        return new ResponseEntity<>(ApiResponse.error(errorCode, message), status);
    }

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ApiResponse<?>> handleBusinessException(BusinessException e) {
        log.error("businessException exception occurred: {}", e.getMessage(), e);
        return newResponseEntity(e.getErrorCode().getCode(), e.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({Exception.class, RuntimeException.class})
    public ResponseEntity<?> handleException(Exception e) {
        log.error("Unexpected exception occurred: {}", e.getMessage(), e);
        return newResponseEntity(e, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
