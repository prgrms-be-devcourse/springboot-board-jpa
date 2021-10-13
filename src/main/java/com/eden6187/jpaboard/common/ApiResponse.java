package com.eden6187.jpaboard.common;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class ApiResponse<T> {

  private int statusCode;
  private T data;
  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
  private LocalDateTime serverDatetime;

  public ApiResponse(int statusCode, T data) {
    this.statusCode = statusCode;
    this.data = data;
    this.serverDatetime = LocalDateTime.now();
  }

  public static <T> ApiResponse<T> ok(T data) {
    return new ApiResponse<>(200, data);
  }

  public static <T> ApiResponse<T> fail(int statusCode, T data) {
    return new ApiResponse<>(statusCode, data);
  }
}

