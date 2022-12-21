package com.prgrms.boardjpa.common;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CommonResponse<T> {
  private T data;
  private ResponseMessage responseMessage;

  public CommonResponse(T data, ResponseMessage message) {
    this.data = data;
    this.responseMessage = message;
  }

  public static <T> CommonResponse<T> ok(T data) {
    return new CommonResponse<>(data, ResponseMessage.SUCCESS);
  }

  public static <T> CommonResponse<T> fail(T data) {
    return new CommonResponse<>(data, ResponseMessage.FAIL);
  }

}
