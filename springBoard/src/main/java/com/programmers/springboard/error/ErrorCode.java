package com.programmers.springboard.error;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.*;

@Getter
@AllArgsConstructor
public enum ErrorCode {

    /* 404 NOT_FOUND : Resource 를 찾을 수 없음 */
    POSTS_NOT_FOUND(NOT_FOUND, "게시글을 찾을 수 없습니다."),
    USERS_NOT_FOUND(NOT_FOUND, "유저를 찾을 수 없습니다."),

    /* 409 CONFLICT : Resource 의 현재 상태와 충돌. */
    DUPLICATE_RESOURCE(CONFLICT, "데이터가 이미 존재합니다"),

    /* 500 INTERNAL_SERVER_ERROR: 내부 서버 오류 */
    SERVER_ERROR(INTERNAL_SERVER_ERROR, "서버 오류입니다."),

    ;

    private final HttpStatus httpStatus;
    private final String message;

}
