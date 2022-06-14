package com.programmers.board.common.exception;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.validation.BindingResult;

public class ErrorResponse {

  private int status;

  private String message;

  private String code;

  private List<FieldError> errors;

  private ErrorResponse() {
  }

  private ErrorResponse(ErrorCode code) {
    this.status = code.getStatus();
    this.message = code.getMessage();
  }

  private ErrorResponse(ErrorCode errorCode, List<FieldError> errors) {
    this(errorCode);
    this.errors = errors;
  }


  public static ErrorResponse of(ErrorCode code) {
    return new ErrorResponse(code);
  }

  public static ErrorResponse of(ErrorCode code, BindingResult bindingResult) {
    return new ErrorResponse(code, FieldError.of(bindingResult));
  }

  //Getter
  public int getStatus() {
    return status;
  }

  public String getMessage() {
    return message;
  }

  public String getCode() {
    return code;
  }

  public List<FieldError> getErrors() {
    return errors;
  }

  //FieldError - Inner Class
  public static class FieldError {

    private String field;

    private String value;

    private String reason;

    private FieldError() {
    }

    private FieldError(String field, String value, String reason) {
      this.field = field;
      this.value = value;
      this.reason = reason;
    }

    public static List<FieldError> of(BindingResult bindingResult) {
      List<org.springframework.validation.FieldError> fieldErrors = bindingResult.getFieldErrors();
      return fieldErrors.stream()
          .map(error -> new FieldError(
              error.getField(),
              error.getRejectedValue() == null ? "" : error.getRejectedValue().toString(),
              error.getDefaultMessage()
          ))
          .collect(Collectors.toList());
    }
  }
}
