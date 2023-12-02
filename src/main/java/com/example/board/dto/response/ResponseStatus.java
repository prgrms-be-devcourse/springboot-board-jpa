package com.example.board.dto.response;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ResponseStatus {
    // 200 ok
    OK(HttpStatus.OK, 20000, "요청 성공"),

    // 400 bad request
    BAD_REQUEST(HttpStatus.BAD_REQUEST, 40000, "잘못된 요청입니다."),

    // 404 not found
    USER_NOT_FOUND(HttpStatus.NOT_FOUND, 40401, "유저를 찾을 수 없습니다."),
    ALREADY_DELETED_USER(HttpStatus.BAD_REQUEST, 40402, "이미 탈퇴한 유저입니다."),
    POST_NOT_FOUND(HttpStatus.NOT_FOUND, 40404, "게시글을 찾을 수 없습니다."),

    // 409 conflict
    DUPLICATED_USER_NAME(HttpStatus.CONFLICT, 40901, "이미 존재하는 이름입니다."),
    AUTHOR_NOT_MATCH(HttpStatus.CONFLICT, 40902, "작성자가 일치하지 않습니다."),

    // 500
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, 50000, "서버 에러. 관리자에게 문의하세요.");

    private final HttpStatus httpStatus;
    private final Integer code;
    private final String message;

    ResponseStatus(HttpStatus httpStatus, Integer code, String message) {
        this.httpStatus = httpStatus;
        this.code = code;
        this.message = message;
    }
}
