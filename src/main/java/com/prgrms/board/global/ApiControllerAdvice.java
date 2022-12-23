package com.prgrms.board.global;

import com.prgrms.board.dto.ApiBindErrorResponse;
import com.prgrms.board.dto.ApiErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class ApiControllerAdvice {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(IllegalArgumentException.class)
    public ApiErrorResponse illegalArgumentExceptionHandler(IllegalArgumentException e) {
        log.warn("IllegalArgumentException 발생-> {}", e.getMessage());
        return ApiErrorResponse.fail(e.getMessage(), 400);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(BindException.class)
    public ApiBindErrorResponse bindExceptionHandler(BindException e) {
        log.warn("BindException 발생-> {}", e.getFieldError());
        return ApiBindErrorResponse.fail(
                e.getFieldError().getObjectName(),
                e.getFieldError().getField(),
                e.getFieldError().getDefaultMessage(),
                400);
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(RuntimeException.class)
    public ApiErrorResponse runTimeExceptionHandler(RuntimeException e) {
        log.warn("RuntimeException 발생-> {}", e.getMessage());
        return ApiErrorResponse.fail(e.getMessage(), 404);
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    public ApiErrorResponse internalServerErrorHandler(Exception e) {
        log.warn("Exception 발생-> {}", e.getMessage());
        return ApiErrorResponse.fail(e.getMessage(), 500);
    }

}
