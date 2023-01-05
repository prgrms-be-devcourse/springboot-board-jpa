package com.prgrms.global.exception;

import com.prgrms.global.error.ErrorCode;

public class LoginFailException extends CustomException{

    public LoginFailException(ErrorCode errorCode) {
        super(errorCode);
    }
}
