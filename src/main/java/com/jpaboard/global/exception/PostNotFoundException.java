package com.jpaboard.global.exception;

public class PostNotFoundException extends RuntimeException {
    private static final String POST_NOT_FOUND_MESSAGE = "조회할 수 있는 Post가 없습니다.";

    public PostNotFoundException() {
        super(POST_NOT_FOUND_MESSAGE);
    }
}
