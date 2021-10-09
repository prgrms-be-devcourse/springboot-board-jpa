package com.programmers.springbootboard.exception;

import com.programmers.springbootboard.exception.error.DuplicationArgumentException;
import com.programmers.springbootboard.exception.error.InvalidArgumentException;
import com.programmers.springbootboard.exception.error.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(InvalidArgumentException.class)
    protected ResponseEntity<ErrorResponseDto> handleInvalidArgumentException(InvalidArgumentException exception) {
        ErrorMessage errorMessage = ErrorMessage.of(exception.getMessage());
        ErrorResponseDto response = ErrorResponseDto.of(errorMessage);
        return new ResponseEntity<>(response, errorMessage.getStatus());
    }

    @ExceptionHandler(NotFoundException.class)
    protected ResponseEntity<ErrorResponseDto> handleNotFoundException(NotFoundException exception) {
        ErrorMessage errorMessage = ErrorMessage.of(exception.getMessage());
        ErrorResponseDto response = ErrorResponseDto.of(errorMessage);
        return new ResponseEntity<>(response, errorMessage.getStatus());
    }

    @ExceptionHandler(DuplicationArgumentException.class)
    protected ResponseEntity<ErrorResponseDto> handleDuplicationArgumentException(DuplicationArgumentException exception) {
        ErrorMessage errorMessage = ErrorMessage.of(exception.getMessage());
        ErrorResponseDto response = ErrorResponseDto.of(errorMessage);
        return new ResponseEntity<>(response, errorMessage.getStatus());
    }

    @ExceptionHandler(Exception.class)
    protected ResponseEntity<ErrorResponseDto> handleException(Exception exception) {
        ErrorMessage errorMessage = ErrorMessage.of(exception.getMessage());
        ErrorResponseDto response = ErrorResponseDto.of(errorMessage);
        return new ResponseEntity<>(response, errorMessage.getStatus());
    }
}
