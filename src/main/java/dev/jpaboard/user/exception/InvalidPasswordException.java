package dev.jpaboard.user.exception;

import dev.jpaboard.common.exception.BadRequestException;

public class InvalidPasswordException extends BadRequestException {
    private static final String MESSAGE = "적절하지 않은 비밀번호입니다.";

    public InvalidPasswordException() {
        super(MESSAGE);
    }

}
