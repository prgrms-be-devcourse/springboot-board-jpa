package com.kdt.board.common.exception;

public class CannotEditPostException extends BusinessException {

    private static final ErrorCode errorCode = ErrorCode.POST_EDIT_UNAUTHORIZED;

    public CannotEditPostException() {
        super(errorCode);
    }
}
