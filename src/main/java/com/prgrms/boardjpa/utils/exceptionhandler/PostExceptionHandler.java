package com.prgrms.boardjpa.utils.exceptionhandler;

import com.prgrms.boardjpa.common.CommonResponse;
import com.prgrms.boardjpa.common.ResponseMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.NoSuchElementException;

@Slf4j
@RestControllerAdvice
public class PostExceptionHandler {

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public CommonResponse<?> handleMethodArgumentNotValidException(MethodArgumentNotValidException exception) {
    log.warn("MethodArgumentNotValidException", exception);
    return new CommonResponse<>(exception.getParameter().toString(), ResponseMessage.INVALID_REQUEST);
  }

  @ExceptionHandler(NoSuchElementException.class)
  public CommonResponse<?> handleNoSuchElementException(NoSuchElementException exception) {
    log.warn("NoSuchElementException", exception);
    return new CommonResponse<>(exception.getMessage(), ResponseMessage.NOT_FOUND);
  }

  @ExceptionHandler(RuntimeException.class)
  public CommonResponse<?> handleRuntimeException(RuntimeException exception) {
    log.error("RuntimeException", exception);
    return new CommonResponse<>(exception.getMessage(), ResponseMessage.FAIL);
  }

  @ExceptionHandler(Exception.class)
  public CommonResponse<?> handleException(Exception exception) {
    log.error("Exception", exception);
    return new CommonResponse<>(exception.getMessage(), ResponseMessage.FAIL);
  }

}
