package org.prgrms.board.domain.post.exception;

import org.prgrms.board.global.exception.ErrorCode;

public class PostException extends RuntimeException {
    private final ErrorCode errorCode;

    public PostException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }

    public ErrorCode getErrorCode() {
        return errorCode;
    }
}
