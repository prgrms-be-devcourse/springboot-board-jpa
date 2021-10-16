package com.eden6187.jpaboard.common;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
@AllArgsConstructor
@Getter
public enum ErrorCode {
  DUPLICATED_USER_NAME("U001", "User 이름 중복", 400),
  USER_NOT_FOUND("U002", "존재하지 않는 USER", 404),
  POST_NOT_FOUND("P001", "존재하지 않는 POST", 404),
  NO_AUTHORIZATION("A001", "POST 수정 권한이 없는 USER입니다.", 400);

  private final String privateCode;
  private final String message;
  private int statusCode;
}
