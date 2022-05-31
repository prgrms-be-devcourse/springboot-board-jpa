package org.prgrms.kdt.common;

public record ErrorResponse(String type, String message) {

  public static ErrorResponse of(ErrorCode errorCode) {
    return new ErrorResponse(errorCode.name(), errorCode.getMessage());
  }
}