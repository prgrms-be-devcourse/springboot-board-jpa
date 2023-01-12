package com.example.springbootboardjpa.global.exception;


import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;


@Getter
@AllArgsConstructor
public enum ErrorCode {

  /* 400 BAD_REQUEST : 잘못된 요청 */
  /* 401 UNAUTHORIZED : 인증되지 않은 사용자 */
  BAD_REQUEST_VALIDATION(HttpStatus.BAD_REQUEST, "검증에 실패하였습니다."),
  INVALID_AUTH_TOKEN(HttpStatus.UNAUTHORIZED, "잘못된 토큰입니다."),
  POST_NOT_FOUND(HttpStatus.NOT_FOUND, "게시글을 찾지 못했습니다."),
  MEMBER_NOT_FOUND(HttpStatus.NOT_FOUND, "회원을 찾지 못했습니다."),
  NOT_HAVA_PERMISSION(HttpStatus.FORBIDDEN, "권한이 없습니다.");

  private final HttpStatus httpStatus;

  private final String message;
}