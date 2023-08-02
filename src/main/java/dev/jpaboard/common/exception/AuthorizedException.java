package dev.jpaboard.common.exception;

public class AuthorizedException extends RuntimeException {

    private static final String MESSAGE = "권한이 없습니다.";

    public AuthorizedException() {
        super(MESSAGE);
    }

}
