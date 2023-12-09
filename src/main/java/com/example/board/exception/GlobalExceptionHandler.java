package com.example.board.exception;

import com.example.board.dto.response.ApiResponse;
import com.example.board.dto.response.ResponseStatus;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<String>> handleException(Exception e) {
        return ResponseEntity.internalServerError().body(ApiResponse.fail(ResponseStatus.INTERNAL_SERVER_ERROR, e.getMessage()));
    }

    @ExceptionHandler(CustomException.class)
    public ResponseEntity<ApiResponse<String>> handleCustomException(CustomException e) {
        ResponseStatus errorResponseStatus = e.getErrorResponseStatus();
        return ResponseEntity.status(errorResponseStatus.getHttpStatus()).body(ApiResponse.fail(errorResponseStatus));
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {

        Map<String, List<String>> errors = ex.getFieldErrors().stream()
                .collect(Collectors.groupingBy(
                        FieldError::getField,
                        Collectors.mapping(FieldError::getDefaultMessage, Collectors.toList())));
        return ResponseEntity
                .status(status)
                .body(ApiResponse.fail(ResponseStatus.INVALID_INPUT_VALUE, errors));
    }
}
