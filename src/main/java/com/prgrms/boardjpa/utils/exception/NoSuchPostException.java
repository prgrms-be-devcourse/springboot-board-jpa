package com.prgrms.boardjpa.utils.exception;

import java.util.NoSuchElementException;

public class NoSuchPostException extends NoSuchElementException {

  public NoSuchPostException() {
    super();
  }

  public NoSuchPostException(String s, Throwable cause) {
    super(s, cause);
  }

  public NoSuchPostException(Throwable cause) {
    super(cause);
  }

  public NoSuchPostException(String s) {
    super(s);
  }
}
