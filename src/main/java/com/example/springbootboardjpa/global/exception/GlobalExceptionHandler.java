package com.example.springbootboardjpa.global.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

  @ExceptionHandler(CustomException.class)
  protected ResponseEntity<ErrorResponse> handleCustomException(CustomException customException,
      Exception e) {
    log.info(e.getLocalizedMessage());
    return ErrorResponse.toResponseEntity(customException.getErrorCode());
  }

  @ExceptionHandler(Exception.class)
  protected ResponseEntity<Object> handleMethodArgumentNotValid(Exception e) {
    log.error(e.getLocalizedMessage());
    CustomException customException = new CustomException(ErrorCode.BAD_REQUEST_VALIDATION);
    return ErrorResponse.toObject(customException);
  }
}