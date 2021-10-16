package com.eden6187.jpaboard.exception.not_found;

import com.eden6187.jpaboard.common.ErrorCode;

public class UserNotFoundException extends NotFoundException {

  public UserNotFoundException(ErrorCode errorCode) {
    super(errorCode);
  }
}
