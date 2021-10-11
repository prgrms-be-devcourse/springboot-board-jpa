package com.programmers.springboard.config;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorMessage {

    BAD_REQUEST("잘못된 요청입니다."),

    POSTS_NOT_FOUND("게시글을 찾을 수 없습니다."),

    INTERNAL_SERVER_ERROR("내부 서버 오류입니다."),

    ;

    private final String message;

}
