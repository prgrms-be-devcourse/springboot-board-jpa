package dev.jpaboard.common.exception;

public class UnauthorizedAccessException extends RuntimeException {

    private static final String MESSAGE = "권한이 없습니다.";

    public UnauthorizedAccessException() {
        super(MESSAGE);
    }

}
