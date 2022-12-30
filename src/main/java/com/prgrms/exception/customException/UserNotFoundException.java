package com.prgrms.exception.customException;

import com.prgrms.exception.ErrorCode;

public class UserNotFoundException extends CustomException {

    public UserNotFoundException(String message, ErrorCode errorCode) {

        super(message, errorCode);
    }

    public UserNotFoundException(ErrorCode errorCode) {

        super(errorCode);
    }

}
