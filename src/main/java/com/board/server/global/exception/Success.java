package com.board.server.global.exception;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public enum Success {
    /**
     * 200 OK
     */
    GET_POST_LIST_SUCCESS(HttpStatus.OK, "게시글 전체 조회 성공"),
    GET_POST_SUCCESS(HttpStatus.OK, "게시글 단일 조회 성공"),
    UPDATE_POST_SUCCESS(HttpStatus.OK, "게시글 수정 성공"),

    /**
     * 201 CREATED
     */
    CREATE_POST_SUCCESS(HttpStatus.CREATED, "게시글 생성 성공"),
    ;

    private final HttpStatus httpStatus;
    private final String message;

    public int getHttpStatusCode() {
        return httpStatus.value();
    }
}
