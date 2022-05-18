package com.programmers.epicblues.board.controller;

import com.programmers.epicblues.board.exception.InvalidRequestArgumentException;
import com.programmers.epicblues.board.exception.ResourceNotFoundException;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice(basePackages = "com.programmers.epicblues.board.controller")
@Slf4j
public class RestControllerExceptionHandler {

  @ResponseStatus(HttpStatus.BAD_REQUEST)
  @ExceptionHandler(InvalidRequestArgumentException.class)
  public Map<String, String> handleValidationException(
      InvalidRequestArgumentException exception) {
    log.error("InvalidRequestArgumentException", exception);
    return exception.getBindingResult().getAllErrors().stream().collect(
        Collectors.toMap(
            error -> ((FieldError) error).getField(),
            error -> error.getDefaultMessage() == null ? "No Message" : error.getDefaultMessage()));
  }

  @ResponseStatus(HttpStatus.NOT_FOUND)
  @ExceptionHandler(ResourceNotFoundException.class)
  public Map<String, String> handleResultNotFoundException(
      ResourceNotFoundException exception) {
    log.error(exception.getMessage(), exception);
    return Map.of("message", exception.getMessage());
  }
}
