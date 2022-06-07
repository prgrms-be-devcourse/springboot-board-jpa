package com.devcourse.springjpaboard.core.exception;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.NOT_FOUND;

import com.devcourse.springjpaboard.core.util.ApiResponse;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler(NotFoundException.class)
  @ResponseStatus(NOT_FOUND)
  public ApiResponse<String> notFoundHandler(NotFoundException e) {
    return ApiResponse.fail(NOT_FOUND, e.getMessage());
  }

  @ExceptionHandler(RuntimeException.class)
  @ResponseStatus(INTERNAL_SERVER_ERROR)
  public ApiResponse<String> internalServerError(Exception e) {
    return ApiResponse.fail(INTERNAL_SERVER_ERROR, e.getMessage());
  }

}
