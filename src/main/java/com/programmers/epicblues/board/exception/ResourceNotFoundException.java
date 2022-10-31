package com.programmers.epicblues.board.exception;

public class ResourceNotFoundException extends RuntimeException {

  public ResourceNotFoundException(String message, Throwable exception) {
    super(message, exception);
  }
}
