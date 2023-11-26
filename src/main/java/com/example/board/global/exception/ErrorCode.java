package com.example.board.global.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;


@Getter
public enum ErrorCode {

    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "서버 내부 오류가 발생했습니다."),
    DUPLICATE_EMAIL(HttpStatus.CONFLICT, "중복된 이메일입니다."),
    MEMBER_NOT_FOUND(HttpStatus.NOT_FOUND, "회원이 존재하지 않습니다."),
    POST_NOT_FOUND(HttpStatus.NOT_FOUND, "게시물이 존재하지 않습니다."),
    WRITER_MISMATCH(HttpStatus.BAD_REQUEST, "게시글 작성자가 일치하지 않습니다.");

    private final HttpStatus status;
    private final String message;

    ErrorCode(final HttpStatus status, final String message) {
        this.status = status;
        this.message = message;
    }

    public static ErrorCode defaultError() {
        return INTERNAL_SERVER_ERROR;
    }
}
