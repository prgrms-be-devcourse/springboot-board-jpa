package dev.jpaboard.user.exception;

import dev.jpaboard.common.exception.NotFoundException;

public class UserNotFoundException extends NotFoundException {
    private static final String MESSAGE = "유저를 찾을 수 없습니다.";

    public UserNotFoundException() {
        super(MESSAGE);
    }

}
