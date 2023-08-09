package com.jpaboard.global.exception;

public class UserNotFoundException extends RuntimeException {
    private static final String USER_NOT_FOUND_MESSAGE = "조회할 수 있는 User가 없습니다.";

    public UserNotFoundException() {
        super(USER_NOT_FOUND_MESSAGE);
    }
}
