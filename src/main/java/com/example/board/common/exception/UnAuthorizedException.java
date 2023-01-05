package com.example.board.common.exception;

public class UnAuthorizedException extends RuntimeException {

  public UnAuthorizedException(String message) {
    super(message);
  }
}
