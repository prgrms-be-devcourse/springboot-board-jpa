package com.prgrms.global.exception;

import com.prgrms.global.error.ErrorCode;

public class PostNotFoundException extends CustomException {

    public PostNotFoundException(String message, ErrorCode errorCode) {

        super(message, errorCode);
    }

}
