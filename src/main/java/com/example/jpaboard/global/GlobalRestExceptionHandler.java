package com.example.jpaboard.global;

import com.example.jpaboard.global.exception.EntityNotFoundException;
import com.example.jpaboard.global.exception.UnauthorizedEditException;

import jakarta.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static org.springframework.http.HttpStatus.*;

@RestControllerAdvice
public class GlobalRestExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(GlobalRestExceptionHandler.class);

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleMethodArgumentNotValidException(HttpServletRequest request, MethodArgumentNotValidException e) {
        StringBuilder resultStringBuilder = getResultStringBuilder(e);

        ErrorResponse errorResponse = getErrorResponse(BAD_REQUEST.value(),
                                                        resultStringBuilder.toString(),
                                                        request.getRequestURI());
        return ResponseEntity
                .status(BAD_REQUEST)
                .body(errorResponse);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResponse> handleIllegalArgumentException(HttpServletRequest request, IllegalArgumentException e) {
        ErrorResponse errorResponse = getErrorResponse(BAD_REQUEST.value(),
                                                        e.getMessage(),
                                                        request.getRequestURI());

        return ResponseEntity.status(BAD_REQUEST).body(errorResponse);
    }

    @ExceptionHandler(UnauthorizedEditException.class)
    public ResponseEntity<ErrorResponse> handleUnauthorizedEditException(HttpServletRequest request, UnauthorizedEditException e) {
        ErrorResponse errorResponse = getErrorResponse(FORBIDDEN.value(),
                                                       e.getMessage(),
                                                       request.getRequestURI());

        return ResponseEntity.status(FORBIDDEN).body(errorResponse);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleNotFoundException(HttpServletRequest request, EntityNotFoundException e) {
        ErrorResponse errorResponse = getErrorResponse(NOT_FOUND.value(),
                                                        e.getMessage(),
                                                        request.getRequestURI());

        return ResponseEntity.status(NOT_FOUND).body(errorResponse);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Void> handleException(HttpServletRequest request, Exception e) {
        logger.error("Exception URI {}", request.getRequestURI());
        logger.error("Sever Exception", e);

        return ResponseEntity.status(INTERNAL_SERVER_ERROR).build();
    }

    private static StringBuilder getResultStringBuilder(MethodArgumentNotValidException e) {
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
