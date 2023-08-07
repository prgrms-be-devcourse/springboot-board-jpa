package com.prgrms.boardjpa.global;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorCode {

    //고객
    INVALID_AGE(HttpStatus.BAD_REQUEST, "올바른 나이를 입력해주세요."),
    INVALID_NAME(HttpStatus.BAD_REQUEST, "올바른 이름을 입력해주세요."),
    DUPLICATED_NAME(HttpStatus.BAD_REQUEST, "이미 존재하는 이름입니다."),
    NOT_FOUND_USER(HttpStatus.NOT_FOUND, "존재하지 않는 유저입니다."),

    //게시물
    NOT_FOUND_POST(HttpStatus.NOT_FOUND, "존재하지 않는 게시물입니다."),
    NOT_MATCH_AUTHOR_MODIFIER(HttpStatus.BAD_REQUEST, "작성자와 수정자가 동일하지 않습니다.");

    private final HttpStatus status;
    private final String message;

    ErrorCode(HttpStatus status, String message) {
        this.status = status;
        this.message = message;
    }
}
