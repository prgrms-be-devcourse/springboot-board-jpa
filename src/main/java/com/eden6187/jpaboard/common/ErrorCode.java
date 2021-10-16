package com.eden6187.jpaboard.common;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
@AllArgsConstructor
@Getter
public enum ErrorCode {
  DUPLICATED_USER_NAME(400, "U001", "User 이름 중복"),
  USER_NOT_FOUND(404,"U002","존재하지 않는 USER");

  private int statusCode;
  private final String privateCode;
  private final String message;
}
