package com.example.board.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum CustomError {

    WRONG_PASSWORD(HttpStatus.BAD_REQUEST, "WRONG_PASSWORD", "이름이나 비밀번호가 일치하지 않습니다."),
    DUPLICATED_USER_NAME(HttpStatus.BAD_REQUEST, "DUPLICATED_USER_NAME", "이미 존재하는 이름입니다."),
    ALREADY_DELETED_USER(HttpStatus.BAD_REQUEST, "ALREADY_DELETED_USER", "이미 탈퇴한 유저입니다."),

    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "USER_NOT_FOUND", "유저를 찾을 수 없습니다."),
    POST_NOT_FOUND(HttpStatus.NOT_FOUND, "POST_NOT_FOUND", "게시글을 찾을 수 없습니다."),

    LOGIN_REQUIRED(HttpStatus.UNAUTHORIZED, "LOGIN_REQUIRED", "로그인이 필요합니다."),
    INVALID_TOKEN(HttpStatus.UNAUTHORIZED, "INVALID_TOKEN", "유효하지 않은 토큰입니다."),

    AUTHOR_NOT_MATCH(HttpStatus.FORBIDDEN, "AUTHOR_NOT_MATCH", "권한이 없습니다."),

    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "INTERNAL_SERVER_ERROR", "서버에 오류가 발생했습니다.");

    private final HttpStatus status;
    private final String code;
    private final String message;

    CustomError(HttpStatus status, String code, String message) {
        this.status = status;
        this.code = code;
        this.message = message;
    }
}
