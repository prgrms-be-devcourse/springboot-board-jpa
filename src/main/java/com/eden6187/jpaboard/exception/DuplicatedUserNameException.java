package com.eden6187.jpaboard.exception;

public class DuplicatedUserNameException extends RuntimeException {

  public DuplicatedUserNameException() {
    super();
  }

  public DuplicatedUserNameException(String message) {
    super(message);
  }

  public DuplicatedUserNameException(Throwable cause) {
    super(cause);
  }

  public DuplicatedUserNameException(String message, Throwable cause) {
    super(message, cause);
  }
}
