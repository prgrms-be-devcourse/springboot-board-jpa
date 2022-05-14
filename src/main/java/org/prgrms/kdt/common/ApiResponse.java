package org.prgrms.kdt.common;

import java.time.LocalDateTime;
import org.springframework.http.HttpStatus;

public record ApiResponse<T>(int statusCode, T data, LocalDateTime serverDateTime) {

  public ApiResponse(int statusCode, T data) {
    this(statusCode, data, LocalDateTime.now());
  }

  public static <T> ApiResponse<T> ok(T data) {
    return new ApiResponse<>(200, data);
  }

  public static <T> ApiResponse<T> of(HttpStatus httpStatus, T data) {
    return new ApiResponse<>(httpStatus.value(), data);
  }

  public static ApiResponse<String> fail(ErrorCode errorCode) {
    return new ApiResponse<>(errorCode.getStatus(), errorCode.getMessage());
  }
}