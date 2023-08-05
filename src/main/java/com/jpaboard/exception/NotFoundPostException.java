package com.jpaboard.exception;

public class NotFoundPostException extends RuntimeException{

    private final static String NOT_FOUND_POST_MESSAGE = "게시글 정보를 찾을 수 없습니다.";

    public NotFoundPostException() {
        super(NOT_FOUND_POST_MESSAGE);
    }
}
