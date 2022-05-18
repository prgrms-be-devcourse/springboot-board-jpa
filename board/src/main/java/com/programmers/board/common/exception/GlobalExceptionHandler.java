package com.programmers.board.common.exception;

import com.programmers.board.api.common.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import javax.persistence.EntityNotFoundException;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value = Exception.class)
    public ApiResponse<ErrorResponse> handleServerException(Exception e){
        log.error(e.getMessage(), e);
        ErrorResponse errorResponse = ErrorResponse.of(ErrorCode.INTERNAL_SERVER_ERROR);
        return ApiResponse.fail(errorResponse.getStatus(),errorResponse);
    }

    @ExceptionHandler(value = HttpRequestMethodNotSupportedException.class)
    public ApiResponse<ErrorResponse> handleHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException e){
        log.error(e.getMessage(), e);
        ErrorResponse errorResponse = ErrorResponse.of(ErrorCode.METHOD_NOT_ALLOWED);
        return ApiResponse.fail(errorResponse.getStatus(),errorResponse);
    }

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public ApiResponse<ErrorResponse> handleMethodArgumentNotValidException (MethodArgumentNotValidException e){
        log.error(e.getMessage(), e);
        ErrorResponse errorResponse = ErrorResponse.of(ErrorCode.INVALID_INPUT_VALUE);
        return ApiResponse.fail(errorResponse.getStatus(),errorResponse);
    }

    @ExceptionHandler(value = MethodArgumentTypeMismatchException.class)
    public ApiResponse<ErrorResponse> handleMethodArgumentNotValidException (MethodArgumentTypeMismatchException e){
        log.error(e.getMessage(), e);
        ErrorResponse errorResponse = ErrorResponse.of(ErrorCode.INVALID_TYPE_VALUE);
        return ApiResponse.fail(errorResponse.getStatus(),errorResponse);
    }

    @ExceptionHandler(value = EntityNotFoundException.class)
    public ApiResponse<ErrorResponse> handleEntityNotFoundException(EntityNotFoundException e){
        log.error(e.getMessage(), e);
        ErrorResponse errorResponse = ErrorResponse.of(ErrorCode.POST_NOT_FOUND);
        return ApiResponse.fail(errorResponse.getStatus(),errorResponse);
    }

}
