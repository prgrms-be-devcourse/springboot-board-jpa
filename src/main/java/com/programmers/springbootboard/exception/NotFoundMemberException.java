package com.excmul.member.exception;

public class NotFoundMemberException extends RuntimeException {
    private static final String ERROR_MESSAGE = "사용자 정보를 찾을 수 없습니다.";

    public NotFoundMemberException() {
        super(ERROR_MESSAGE);
    }
}
