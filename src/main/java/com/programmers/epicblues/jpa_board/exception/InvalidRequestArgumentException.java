package com.programmers.epicblues.jpa_board.exception;

import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;

public class InvalidRequestArgumentException extends RuntimeException {

  private final transient BindingResult bindingResult;

  public InvalidRequestArgumentException(BindingResult bindingResult) {
    this.bindingResult = bindingResult;
  }

  public Errors getBindingResult() {
    return bindingResult;
  }
}
