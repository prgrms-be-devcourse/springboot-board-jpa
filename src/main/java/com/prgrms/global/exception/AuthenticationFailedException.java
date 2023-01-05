package com.prgrms.global.exception;

import com.prgrms.global.error.ErrorCode;

public class AuthenticationFailedException extends CustomException{

    public AuthenticationFailedException(ErrorCode errorCode) {
        super(errorCode);
    }

    public AuthenticationFailedException(String message, ErrorCode errorCode) {
        super(message, errorCode);
    }
}
