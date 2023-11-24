package com.kdt.simpleboard.common.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {
    NOT_EXIST_USER_ID(HttpStatus.NOT_FOUND, "해당 아이디의 회원이 존재하지 않습니다."),
    USER_ALREADY_EXISTS(HttpStatus.BAD_REQUEST, "해당 이름의 회원이 이미 존재합니다."),

    Not_EXIST_BOARD(HttpStatus.BAD_REQUEST, "해당 아이디의 게시글이 존재하지 않습니다");

    private final HttpStatus status;
    private final String message;
}
