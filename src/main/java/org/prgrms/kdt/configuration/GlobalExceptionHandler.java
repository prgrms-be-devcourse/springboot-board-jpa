package org.prgrms.kdt.configuration;

import javax.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.prgrms.kdt.common.ErrorCode;
import org.prgrms.kdt.common.ErrorResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@Slf4j
@RestControllerAdvice(basePackages = "org.prgrms.kdt.controller")
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

  @Override
  protected ResponseEntity<Object> handleServletRequestBindingException(
      ServletRequestBindingException ex, HttpHeaders headers, HttpStatus status,
      WebRequest request) {
    log.error("필요한 헤더 값을 입력하지 않았습니다. {}", ex.getMessage());
    return ResponseEntity.badRequest().body(ErrorResponse.of(ErrorCode.BAD_REQUEST));
  }

  @Override
  protected ResponseEntity<Object> handleMissingServletRequestParameter(
      MissingServletRequestParameterException ex, HttpHeaders headers, HttpStatus status,
      WebRequest request) {
    log.error("필요한 파라미터 값을 입력하지 않았습니다. {}", ex.getMessage());

    return ResponseEntity.badRequest().body(ErrorResponse.of(ErrorCode.BAD_REQUEST));
  }

  @Override
  protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
      HttpHeaders headers, HttpStatus status, WebRequest request) {
    log.error("잘못된 입력값으로 요청했습니다. {}", ex.getTarget());
    return ResponseEntity.badRequest().body(ErrorResponse.of(ErrorCode.BAD_REQUEST));
  }

  @ExceptionHandler(EntityNotFoundException.class)
  protected ResponseEntity<Object> handleEntityNotFound(EntityNotFoundException ex) {
    log.error(ex.getMessage());
    return ResponseEntity.badRequest().body(ErrorResponse.of(ErrorCode.ENTITY_NOT_FOUND));
  }

  @ExceptionHandler(Exception.class)
  protected ResponseEntity<Object> handleServer(Exception ex) {
    log.error("예상하지 못한 에러가 발생했습니다 : {}", ex.getMessage());
    return ResponseEntity.internalServerError()
        .body(ErrorResponse.of(ErrorCode.INTERNAL_SERVER_ERROR));
  }
}