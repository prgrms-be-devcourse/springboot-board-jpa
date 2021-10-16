package com.eden6187.jpaboard.exception;

import com.eden6187.jpaboard.common.ErrorCode;

public class AuthorizationException extends CustomException {

  public AuthorizationException(ErrorCode errorCode) {
    super(errorCode);
  }
}
