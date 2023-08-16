package com.jpaboard.exception;

public class NotFoundUserException extends RuntimeException{

    private static final String NOT_FOUND_USER_MESSAGE = "회원 정보를 찾을 수 없습니다.";

    public NotFoundUserException() {
        super(NOT_FOUND_USER_MESSAGE);
    }
}
