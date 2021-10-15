package com.prgrms.dlfdyd96.board.common.handler;

import com.prgrms.dlfdyd96.board.common.api.ApiResponse;
import javassist.NotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class ControllerExceptionHandler {

  @ExceptionHandler
  @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
  private ApiResponse<String> exceptionHandler(Exception exception) {
    return ApiResponse.fail(HttpStatus.INTERNAL_SERVER_ERROR.value(), exception.getMessage());
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  private ApiResponse<String> badRequestHandler(Exception exception) {
    return ApiResponse.fail(HttpStatus.BAD_REQUEST.value(), exception.getMessage());
  }

  @ExceptionHandler(NotFoundException.class)
  @ResponseStatus(HttpStatus.NOT_FOUND)
  private ApiResponse<String> notFoundHandler(NotFoundException exception) {
    return ApiResponse.fail(HttpStatus.NOT_FOUND.value(), exception.getMessage());
  }

}
