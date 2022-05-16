package org.prgrms.board.domain.user.exception;

import org.prgrms.board.global.exception.ErrorCode;

public class UserException extends RuntimeException{
    private final ErrorCode errorCode;

    public UserException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }

    public ErrorCode getErrorCode() {
        return errorCode;
    }
}
