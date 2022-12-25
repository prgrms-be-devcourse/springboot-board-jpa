package com.prgrms.be.app.controller;

import com.prgrms.be.app.domain.dto.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.persistence.EntityNotFoundException;

@Slf4j
@RestControllerAdvice
public class ControllerExceptionHandler<T> {

    @ExceptionHandler(EntityNotFoundException.class)
    protected ApiResponse<String> notFoundErrorHandler(EntityNotFoundException exception) {
        log.warn("EntityNotFoundException : ", exception);
        return ApiResponse.fail(404, exception.getMessage(), ResponseMessage.ERRORED);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    protected ApiResponse<String> invalidParameterErrorHandler(IllegalArgumentException exception) {
        log.warn("IllegalArgumentException : ", exception);
        return ApiResponse.fail(400, exception.getMessage(), ResponseMessage.ERRORED);
    }

    @ExceptionHandler(Exception.class)
    protected ApiResponse<String> internalServerErrorHandler(Exception exception) {
        log.error("IntervalServerError : ", exception);
        return ApiResponse.fail(500, exception.getMessage(), ResponseMessage.ERRORED);
    }

}
