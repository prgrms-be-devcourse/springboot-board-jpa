package com.prgrms.springboard.global.error;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.prgrms.springboard.global.common.ApiResponse;
import com.prgrms.springboard.global.error.exception.BusinessException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiResponse<String> handleNotValidException(MethodArgumentNotValidException e) {
        return ApiResponse.fail(HttpStatus.BAD_REQUEST.value(), extractErrorMessage(e));
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
    public ApiResponse<String> handleHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException e) {
        logger.error(e.getMessage(), e);
        return ApiResponse.fail(HttpStatus.METHOD_NOT_ALLOWED.value(), "지원하지 않은 HTTP method 입니다.");
    }

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ApiResponse<String>> handleBusinessException(BusinessException e) {
        return new ResponseEntity<>(ApiResponse.fail(e.getHttpStatus().value(), e.getMessage()), e.getHttpStatus());
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ApiResponse<String> handleException(Exception e) {
        logger.error(e.getMessage(), e);
        return ApiResponse.fail(HttpStatus.INTERNAL_SERVER_ERROR.value(), "서버에서 에러가 발생했습니다.");
    }

    private String extractErrorMessage(MethodArgumentNotValidException exception) {
        return exception.getBindingResult().getAllErrors().get(0).getDefaultMessage();
    }
}
