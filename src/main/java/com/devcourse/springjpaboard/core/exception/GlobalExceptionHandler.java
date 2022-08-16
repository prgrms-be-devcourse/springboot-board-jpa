package com.devcourse.springjpaboard.core.exception;

import static com.devcourse.springjpaboard.core.exception.ExceptionMessage.UNKNOWN;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.NOT_FOUND;

import com.devcourse.springjpaboard.core.util.ApiResponse;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
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

  // TODO fieldError가 null이 될 수 있다고 경고하는데 이를 어떻게 체크해야할까?
  @ExceptionHandler(MethodArgumentNotValidException.class)
  @ResponseStatus(BAD_REQUEST)
  public ApiResponse<ExceptionFormat> badRequestHandler(MethodArgumentNotValidException e) {
    BindingResult bindingResult = e.getBindingResult();

    ExceptionFormat exceptionFormat;
    FieldError fieldError = bindingResult.getFieldError();
    if (fieldError != null) {
      exceptionFormat = new ExceptionFormat(
          fieldError.getDefaultMessage(),
          fieldError.getRejectedValue()
      );
    } else {
      exceptionFormat = new ExceptionFormat(
          UNKNOWN,
          null
      );
    }
    return ApiResponse.fail(BAD_REQUEST, exceptionFormat);
  }

  @ExceptionHandler(RuntimeException.class)
  @ResponseStatus(INTERNAL_SERVER_ERROR)
  public ApiResponse<String> internalServerError(Exception e) {
    return ApiResponse.fail(INTERNAL_SERVER_ERROR, e.getMessage());
  }

}
