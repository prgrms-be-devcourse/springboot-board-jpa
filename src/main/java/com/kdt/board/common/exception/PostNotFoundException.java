package com.kdt.board.common.exception;

public class PostNotFoundException extends BusinessException {

    private static final ErrorCode errorCode = ErrorCode.POST_NOT_FOUND;

    public PostNotFoundException() {
        super(errorCode);
    }
}
