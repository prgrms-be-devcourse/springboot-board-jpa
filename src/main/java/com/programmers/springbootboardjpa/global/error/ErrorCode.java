package com.programmers.springbootboardjpa.global.error;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorCode {

    INVALID_POST_TITLE(HttpStatus.BAD_REQUEST, "게시글 제목은 1자 이상 50자 이하로 입력해주세요."),

    INVALID_POST_CONTENT(HttpStatus.BAD_REQUEST, "내용은 공백일 수 없습니다."),

    INVALID_POST_WRITER(HttpStatus.BAD_REQUEST, "작성자가 포함되어 있어야 합니다."),

    INVALID_USER_NAME_LENGTH(HttpStatus.BAD_REQUEST, "회원 이름은 1자 이상 20자 이하로 입력해주세요."),

    INVALID_USER_NAME_PATTERN(HttpStatus.BAD_REQUEST, "회원 이름은 한글 또는 영어로 입력해주세요."),

    INVALID_USER_AGE(HttpStatus.BAD_REQUEST, "나이는 0세보다 적을 수 없습니다."),

    INVALID_USER_HOBBY(HttpStatus.BAD_REQUEST, "취미는 1자 이상 30자 이하로 입력해주세요."),

    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "회원을 찾을 수 없습니다."),

    POST_NOT_FOUND(HttpStatus.NOT_FOUND, "게시물을 찾을 수 없습니다."),

    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "내부 서버 에러");

    private final HttpStatus status;
    private final String message;
}
