package com.prgrms.hyuk.web.exhandler;

import com.prgrms.hyuk.dto.ApiResponse;
import com.prgrms.hyuk.exception.ClientException;
import com.prgrms.hyuk.exception.InvalidPostIdException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ApiExceptionController {

    @ExceptionHandler
    public ApiResponse<String> ExceptionHandle(Exception exception) {
        return ApiResponse.fail(HttpStatus.INTERNAL_SERVER_ERROR.value(), exception.getMessage());
    }

    @ExceptionHandler(ClientException.class)
    public ApiResponse<String> invalidClientRequestHandle(ClientException exception) {
        return ApiResponse.fail(HttpStatus.BAD_REQUEST.value(), exception.getMessage());
    }

    @ExceptionHandler(InvalidPostIdException.class)
    public ApiResponse<String> invalidPostIdHandle(InvalidPostIdException exception) {
        return ApiResponse.fail(HttpStatus.NOT_FOUND.value(), exception.getMessage());
    }
}
