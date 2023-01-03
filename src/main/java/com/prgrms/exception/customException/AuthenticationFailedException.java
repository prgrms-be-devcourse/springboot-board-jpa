package com.prgrms.exception.customException;

import com.prgrms.exception.ErrorCode;

public class AuthenticationFailedException extends CustomException{

    public AuthenticationFailedException(ErrorCode errorCode) {
        super(errorCode);
    }
}
