package com.prgrms.exception.customException;

import com.prgrms.exception.ErrorCode;

public class EmailDuplicateException extends CustomException {

    public EmailDuplicateException(String message, ErrorCode errorCode) {
        
        super(message, errorCode);
    }

}
