package com.prgrms.board.exception;

import com.prgrms.board.post.api.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class ApiExceptionHandler {
    private static final String BAD_REQUEST_CODE = "400";
    private static final String INTERNAL_SERVER_ERROR_CODE = "500";

    @ExceptionHandler(RuntimeException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse badRequestException(RuntimeException e) {
        log.error("RuntimeExceptionHandler : {}", e.getMessage());
        return new ErrorResponse(HttpStatus.BAD_REQUEST, BAD_REQUEST_CODE, e.getMessage());
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResponse exceptionHandler(Exception e) {
        log.error("exceptionHandler : {}", e.getMessage());
        return new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, INTERNAL_SERVER_ERROR_CODE, e.getMessage());
    }
}
