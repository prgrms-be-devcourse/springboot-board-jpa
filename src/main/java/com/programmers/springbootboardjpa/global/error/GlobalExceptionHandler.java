package com.programmers.springbootboardjpa.global.error;

import com.programmers.springbootboardjpa.global.error.exception.InvalidEntityValueException;
import com.programmers.springbootboardjpa.global.error.exception.NotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(InvalidEntityValueException.class)
    private ErrorResponse invalidEntityValueExceptionHandler(InvalidEntityValueException exception) {
        log.error("handle invalidEntityValueException: {}", exception.getMessage());

        return new ErrorResponse(exception.getErrorCode());
    }

    @ExceptionHandler(NotFoundException.class)
    private ErrorResponse notFoundExceptionHandler(NotFoundException exception) {
        log.error("handle notFoundException: {}", exception.getMessage());

        return new ErrorResponse(exception.getErrorCode());
    }

    @ExceptionHandler(Exception.class)
    private ErrorResponse exceptionHandler(Exception exception) {
        log.error("handle exception: {}", exception.getMessage());

        return new ErrorResponse(ErrorCode.INTERNAL_SERVER_ERROR, exception.getMessage());
    }
}