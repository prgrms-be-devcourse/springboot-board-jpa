package com.prgrms.board.exception;

import com.prgrms.board.post.api.ErrorResponse;
import javassist.NotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class ApiExceptionHandler {
    @ExceptionHandler(IllegalArgumentException.class)
    public ErrorResponse illegalArgumentHandler(IllegalArgumentException e) {
        log.error("IllegalArgumentHandler : {}", e.getMessage());
        return new ErrorResponse(HttpStatus.BAD_REQUEST, "400", e.getMessage());
    }

    @ExceptionHandler(NotFoundException.class)
    public ErrorResponse notFoundExceptionHandler(NotFoundException e) {
        log.error("NotFoundExceptionHandler : {}", e.getMessage());
        return new ErrorResponse(HttpStatus.NOT_FOUND, "404", e.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public ErrorResponse exceptionHandler(Exception e) {
        log.error("exceptionHandler : {}", e.getMessage());
        return new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, "500", e.getMessage());
    }
}
