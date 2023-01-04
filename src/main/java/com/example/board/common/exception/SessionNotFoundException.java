package com.example.board.common.exception;

public class SessionNotFoundException extends RuntimeException {

  public SessionNotFoundException(String message) {
    super(message);
  }
}
