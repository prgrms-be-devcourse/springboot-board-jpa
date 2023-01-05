package com.prgrms.exception.customException;

import com.prgrms.exception.ErrorCode;

public class PostNotFoundException extends CustomException {

    public PostNotFoundException(String message, ErrorCode errorCode) {

        super(message, errorCode);
    }

    public PostNotFoundException(ErrorCode errorCode) {

        super(errorCode);
    }

}
