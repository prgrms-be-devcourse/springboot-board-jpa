package com.prgrms.exception.customException;

import com.prgrms.exception.ErrorCode;

public class UserNotFoundException extends RuntimeException{

    private final ErrorCode errorCode;

    public UserNotFoundException(String message, ErrorCode errorCode) {
        super(message);
        this.errorCode = errorCode;
    }

    public ErrorCode getErrorCode() {
        return errorCode;
    }
}
