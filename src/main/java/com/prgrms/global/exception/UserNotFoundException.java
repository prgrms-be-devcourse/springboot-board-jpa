package com.prgrms.global.exception;

import com.prgrms.global.error.ErrorCode;

public class UserNotFoundException extends CustomException {

    public UserNotFoundException(String message, ErrorCode errorCode) {

        super(message, errorCode);
    }

}
