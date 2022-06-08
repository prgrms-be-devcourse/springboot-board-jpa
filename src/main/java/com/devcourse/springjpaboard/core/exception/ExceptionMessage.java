package com.devcourse.springjpaboard.core.exception;

public class ExceptionMessage {

  private ExceptionMessage() {
  }

  public static final String NOT_FOUND_POST = "해당하는 게시글을 찾을 수 없습니다.";

  public static final String NOT_FOUND_USER = "해당하는 유저를 찾을 수 없습니다.";

  public static final String BLANK_NAME = "이름을 입력 해주세요.";

  public static final String INVALID_RANGE_AGE = "나이는 0 ~ 200 범위 여야 합니다.";

  public static final String BLANK_HOBBY = "취미를 입력 해주세요.";

  public static final String UNKNOWN = "예외가 발생한 이유를 알 수 없습니다.";

  public static final String BLANK_TITLE = "제목을 입력 해주세요.";

  public static final String BLANK_CONTENT = "본문을 입력 해주세요.";

  public static final String NOT_VALID_USER_ID = "잘못된 유저 아이디 입니다.";

}
