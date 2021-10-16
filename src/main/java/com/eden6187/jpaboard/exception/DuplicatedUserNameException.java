package com.eden6187.jpaboard.exception;

import com.eden6187.jpaboard.common.ErrorCode;

public class DuplicatedUserNameException extends CustomException {

  public DuplicatedUserNameException(ErrorCode errorCode) {
    super(errorCode);
  }
}
