package org.prgrms.kdt.common;

import lombok.Getter;

@Getter
public enum ErrorCode {
  BAD_REQUEST(400, "잘못된 입력 요청입니다."),
  NOT_FOUND(404, "해당하는 API를 찾을 수 없습니다."),
  ENTITY_NOT_FOUND(400, "해당하는 엔티티를 찾을 수 없습니다."),
  INTERNAL_SERVER_ERROR(500, "서버 에러가 발생했습니다.");

  private final int status;
  private final String message;

  ErrorCode(int status, String message) {
    this.status = status;
    this.message = message;
  }
}