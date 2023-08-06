package com.example.jpaboard.global;

import com.example.jpaboard.global.exception.EntityNotFoundException;
import com.example.jpaboard.global.exception.PermissionDeniedEditException;

import jakarta.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MissingRequestHeaderException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.servlet.NoHandlerFoundException;

@RestControllerAdvice
public class GlobalRestExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(GlobalRestExceptionHandler.class);

    @ExceptionHandler(BindException.class)
    public ResponseEntity<ErrorResponse> handleBindException(HttpServletRequest request, BindException e) {
        StringBuilder resultStringBuilder = getResultStringBuilder(e);
        int statusCode = HttpStatus.BAD_REQUEST.value();

        return ResponseEntity.status(statusCode).body(getErrorResponse(statusCode, resultStringBuilder.toString(), request.getRequestURI()));
    }

    @ExceptionHandler({IllegalArgumentException.class, MissingRequestHeaderException.class, HttpMessageNotReadableException.class,
            HttpClientErrorException.class, NoHandlerFoundException.class})
    public ResponseEntity<ErrorResponse> handleBadRequestException(HttpServletRequest request, Exception e) {
        int statusCode = HttpStatus.BAD_REQUEST.value();

        return ResponseEntity.status(statusCode).body(getErrorResponse(statusCode, e.getMessage(), request.getRequestURI()));
    }

    @ExceptionHandler(PermissionDeniedEditException.class)
    public ResponseEntity<ErrorResponse> handlePermissionDeniedEditException(HttpServletRequest request, PermissionDeniedEditException e) {
        int statusCode = HttpStatus.FORBIDDEN.value();

        return ResponseEntity.status(statusCode).body(getErrorResponse(statusCode, e.getMessage(), request.getRequestURI()));
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleNotFoundException(HttpServletRequest request, EntityNotFoundException e) {
        int statusCode = HttpStatus.NOT_FOUND.value();

        return ResponseEntity.status(statusCode).body(getErrorResponse(statusCode, e.getMessage(), request.getRequestURI()));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Void> handleException(HttpServletRequest request, Exception e) {
        logger.error("Sever Exception: ", e.getMessage());

        return ResponseEntity.ok().build();
    }

    private static StringBuilder getResultStringBuilder(BindException e) {
        BindingResult bindingResult = e.getBindingResult();
        StringBuilder stringBuilder = new StringBuilder();

        for (FieldError fieldError : bindingResult.getFieldErrors()) {
            stringBuilder.append(fieldError.getField()).append(":");
            stringBuilder.append(fieldError.getDefaultMessage());
            stringBuilder.append(", ");
        }
        return stringBuilder;
    }

    private static ErrorResponse getErrorResponse(int status, String masesage, String requestURI) {
        return new ErrorResponse(status, masesage, requestURI);
    }

}
