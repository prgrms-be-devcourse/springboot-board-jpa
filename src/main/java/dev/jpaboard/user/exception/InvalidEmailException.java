package dev.jpaboard.user.exception;

import dev.jpaboard.common.exception.BadRequestException;

public class InvalidEmailException extends BadRequestException {
    private static final String MESSAGE = "적절하지 않은 이메일입니다.";

    public InvalidEmailException() {
        super(MESSAGE);
    }

}
