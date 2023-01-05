package com.prgrms.exception.customException;

import com.prgrms.exception.ErrorCode;

public class LoginFailException extends CustomException{

    public LoginFailException(ErrorCode errorCode) {
        super(errorCode);
    }
}
