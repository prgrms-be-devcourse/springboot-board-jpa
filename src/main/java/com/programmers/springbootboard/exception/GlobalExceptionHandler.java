package com.programmers.springbootboard.exception;

import com.programmers.springbootboard.exception.error.*;
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

    // TODO
    @ExceptionHandler(InvalidMediaTypeException.class)
    protected ResponseEntity<ErrorResponseDto> handleInvalidMediaTypeException() {
        ErrorMessage errorMessage = ErrorMessage.UNSUPPORTED_MEDIA_TYPE;
        ErrorResponseDto response = ErrorResponseDto.of(errorMessage);
        return new ResponseEntity<>(response, errorMessage.getStatus());
    }

    @ExceptionHandler(Exception.class)
    protected ResponseEntity<ErrorResponseDto> handleException(Exception exception) {
        ErrorMessage errorMessage = ErrorMessage.INTERNAL_SERVER_ERROR;
        ErrorResponseDto response = ErrorResponseDto.of(errorMessage);
        return new ResponseEntity<>(response, errorMessage.getStatus());
    }
}
