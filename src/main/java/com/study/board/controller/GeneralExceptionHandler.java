package com.study.board.controller;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.TypeMismatchException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;

import static com.study.board.controller.GeneralExceptionHandler.ApiError.newResponse;


@Slf4j
@RestControllerAdvice
public class GeneralExceptionHandler {

    @ExceptionHandler(NoHandlerFoundException.class)
    public ResponseEntity<ApiError> handleNotFoundException(Exception e) {
        log.debug("Not Found exception occurred: {}", e.getMessage(), e);
        return newResponse(e, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler({
            IllegalStateException.class, IllegalArgumentException.class,
            TypeMismatchException.class, HttpMessageNotReadableException.class,
            MissingServletRequestParameterException.class,
    })
    public ResponseEntity<ApiError> handleBadRequestException(Exception e) {
        log.debug("Bad request exception occurred: {}", e.getMessage(), e);
        return newResponse(e, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({Exception.class, RuntimeException.class})
    public ResponseEntity<ApiError> handleException(Exception e) {
        log.error("Unexpected exception occurred: {}", e.getMessage(), e);
        return newResponse(e, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Getter
    static class ApiError {
        private final String message;
        private final HttpStatus status;

        private ApiError(Throwable throwable, HttpStatus status) {
            this.message = throwable.getMessage();
            this.status = status;
        }

        static ResponseEntity<ApiError> newResponse(Throwable throwable, HttpStatus status) {
            return new ResponseEntity<>(new ApiError(throwable, status), status);
        }
    }
}
