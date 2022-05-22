package com.kdt.board.common.exception;

public class UserNotFoundException extends BusinessException {

    private static final ErrorCode errorCode = ErrorCode.USER_NOT_FOUND;

    public UserNotFoundException() {
        super(errorCode);
    }
}
