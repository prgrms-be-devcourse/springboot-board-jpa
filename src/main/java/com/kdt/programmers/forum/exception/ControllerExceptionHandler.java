package com.kdt.programmers.forum.exception;

import com.kdt.programmers.forum.transfer.response.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class ControllerExceptionHandler {
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NotFoundException.class)
    public ErrorResponse<String> notFoundHandler(NotFoundException e) {
        log.error(e.getMessage(), e);
        return ErrorResponse.response(e.getMessage());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(IllegalArgumentException.class)
    public ErrorResponse<String> badRequestException(IllegalArgumentException e) {
        log.error("bad request error", e);
        return ErrorResponse.response(e.getMessage());
    }
}
