package com.prgrms.dev.springbootboardjpa.exception;

import com.prgrms.dev.springbootboardjpa.ApiResponse;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final int NOT_FOUND = 404;
    private static final int INTERNAL_SERVER_ERROR = 500;

    @ExceptionHandler(ChangeSetPersister.NotFoundException.class)
    public ApiResponse<String> notFoundHandler(ChangeSetPersister.NotFoundException e) {
        return ApiResponse.fail(NOT_FOUND, e.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public ApiResponse<String> internalServerErrorHandler(Exception e) {
        return ApiResponse.fail(INTERNAL_SERVER_ERROR, e.getMessage());
    } // 다음 타자 .................
}
