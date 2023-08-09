package com.programmers.springbootboardjpa.global.error;

import com.programmers.springbootboardjpa.global.error.exception.InvalidEntityValueException;
import com.programmers.springbootboardjpa.global.error.exception.NotFoundException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(InvalidEntityValueException.class)
    private ErrorResponse invalidEntityValueExceptionHandler(InvalidEntityValueException exception) {

        return new ErrorResponse(exception.getErrorCode());
    }

    @ExceptionHandler(NotFoundException.class)
    private ErrorResponse notFoundExceptionHandler(NotFoundException exception) {

        return new ErrorResponse(exception.getErrorCode());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    private ErrorResponse methodArgumentNotValidExceptionHandler(MethodArgumentNotValidException exception) {

        return new ErrorResponse(ErrorCode.INVALID_ENTITY_VALUE, exception.getBindingResult().getFieldErrors().get(0).getDefaultMessage());
    }

    @ExceptionHandler(Exception.class)
    private ErrorResponse exceptionHandler(Exception exception) {

        return new ErrorResponse(ErrorCode.INTERNAL_SERVER_ERROR, exception.getMessage());
    }
}