package com.devcourse.bbs.exception;

import com.devcourse.bbs.controller.bind.ApiResponse;
import com.devcourse.bbs.controller.bind.FieldErrorDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.List;
import java.util.stream.Collectors;

@ControllerAdvice
public class ControllerExceptionHandler {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<Object>> handleMethodArgumentNotValid(MethodArgumentNotValidException exception) {
        List<FieldErrorDetail> fieldErrorDetails = exception.getFieldErrors().stream()
                .map(fieldError -> new FieldErrorDetail(fieldError.getField(), fieldError.getDefaultMessage()))
                .collect(Collectors.toList());
        String errorMessage = "Request parameter errors on " +
                exception.getFieldErrorCount() +
                " fields. Check result fields for details.";
        return ResponseEntity.badRequest().body(ApiResponse.fail(fieldErrorDetails, errorMessage));
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ApiResponse<Object>> handleHttpMessageNotReadable(HttpMessageNotReadableException exception) {
        return ResponseEntity.badRequest().body(ApiResponse.fail(null, "HTTP request body not readable."));
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ApiResponse<Object>> handleIllegalArgument(IllegalArgumentException exception) {
        return ResponseEntity.badRequest().body(ApiResponse.fail(exception.getMessage()));
    }
}
