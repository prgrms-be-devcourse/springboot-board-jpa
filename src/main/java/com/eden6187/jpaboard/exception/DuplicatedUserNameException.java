package com.eden6187.jpaboard.exception;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class DuplicatedUserNameException extends RuntimeException{
  public DuplicatedUserNameException(Throwable cause) {
    super(cause);
  }
}
