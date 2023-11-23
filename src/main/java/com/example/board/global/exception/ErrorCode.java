package com.example.board.global.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.*;

@Getter
public enum ErrorCode {

    DUPLICATE_EMAIL(CONFLICT, "중복된 이메일입니다."),
    MEMBER_NOT_FOUND(NOT_FOUND, "회원이 존재하지 않습니다."),
    POST_NOT_FOUND(NOT_FOUND, "게시물이 존재하지 않습니다."),
    WRITER_MISMATCH(BAD_REQUEST, "게시글 작성자가 일치하지 않습니다.");

    private final HttpStatus status;
    private final String message;

    ErrorCode(final HttpStatus status, final String message) {
        this.status = status;
        this.message = message;
    }
}
