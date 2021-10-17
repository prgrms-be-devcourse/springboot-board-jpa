package com.homework.springbootboard.exception;

import com.homework.springbootboard.dto.ApiResponse;
import com.homework.springbootboard.dto.ExceptionResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(Exception.class)
    public final ApiResponse<ExceptionResponse> handleAllExceptions(Exception ex, WebRequest request) {
        ExceptionResponse exceptionResponse = new ExceptionResponse(ex.getMessage(), request.getDescription(false));
        return ApiResponse.fail(ErrorStatus.INTERNAL_SERVER_ERROR, ErrorStatus.INTERNAL_SERVER_ERROR.getStatus().value(), exceptionResponse);
    }

    @ExceptionHandler(PostNotFoundException.class)
    public final ApiResponse<ExceptionResponse> handlePostNotFoundException(PostNotFoundException ex, WebRequest request) {
        ExceptionResponse exceptionResponse = new ExceptionResponse(ex.getMessage(), request.getDescription(false));
        return ApiResponse.fail(ErrorStatus.POST_NOT_FOUND, ErrorStatus.POST_NOT_FOUND.getStatus().value(), exceptionResponse);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("errorStatus", status);
        body.put("errorCode", status.value());
        List<String> errors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .collect(Collectors.toList());
        body.put("errors", errors);
        body.put("serverDateTime", LocalDateTime.now());

        return new ResponseEntity<>(body, headers, status);
    }
}
