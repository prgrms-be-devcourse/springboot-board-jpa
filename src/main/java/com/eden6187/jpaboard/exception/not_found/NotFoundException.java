package com.eden6187.jpaboard.exception.not_found;

import com.eden6187.jpaboard.common.ErrorCode;
import com.eden6187.jpaboard.exception.CustomException;

public class NotFoundException extends CustomException {

  public NotFoundException(ErrorCode errorCode) {
    super(errorCode);
  }
}
