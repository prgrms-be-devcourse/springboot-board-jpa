package com.spring.board.springboard.domain;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

@RestControllerAdvice
public class GlobalExceptionHandler {


    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<List<ValidationErrorResponse>> handleDtoValidationException(MethodArgumentNotValidException methodArgumentNotValidException){
        List<ValidationErrorResponse> responses = methodArgumentNotValidException.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(fieldError -> new ValidationErrorResponse(
                        fieldError.getField(),
                        fieldError.getDefaultMessage()
                )
                ).toList();

        return ResponseEntity.badRequest().body(responses);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResponse> handleEntityValidationException(IllegalArgumentException illegalArgumentException){
        ErrorResponse errorResponse = new ErrorResponse(illegalArgumentException.getMessage());

        return ResponseEntity.badRequest().body(errorResponse);
    }
}
