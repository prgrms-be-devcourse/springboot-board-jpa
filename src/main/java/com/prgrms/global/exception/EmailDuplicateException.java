package com.prgrms.global.exception;

import com.prgrms.global.error.ErrorCode;

public class EmailDuplicateException extends CustomException {

    public EmailDuplicateException(String message, ErrorCode errorCode) {
        
        super(message, errorCode);
    }

}
