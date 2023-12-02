package com.example.board.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorCode {

    WRONG_PASSWORD(HttpStatus.BAD_REQUEST, "이름이나 비밀번호가 일치하지 않습니다."),
    DUPLICATED_USER_NAME(HttpStatus.BAD_REQUEST, "이미 존재하는 이름입니다."),
    ALREADY_DELETED_USER(HttpStatus.BAD_REQUEST, "이미 탈퇴한 유저입니다."),
    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "유저를 찾을 수 없습니다."),
    POST_NOT_FOUND(HttpStatus.NOT_FOUND, "게시글을 찾을 수 없습니다."),
    AUTHOR_NOT_MATCH(HttpStatus.FORBIDDEN, "작성자가 일치하지 않습니다.");

    private final HttpStatus status;
    private final String message;

    ErrorCode(HttpStatus status, String message) {
        this.status = status;
        this.message = message;
    }
}
