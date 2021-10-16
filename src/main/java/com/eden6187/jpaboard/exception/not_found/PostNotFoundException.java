package com.eden6187.jpaboard.exception.not_found;

import com.eden6187.jpaboard.common.ErrorCode;

public class PostNotFoundException extends NotFoundException {

  public PostNotFoundException(ErrorCode errorCode) {
    super(errorCode);
  }
}
