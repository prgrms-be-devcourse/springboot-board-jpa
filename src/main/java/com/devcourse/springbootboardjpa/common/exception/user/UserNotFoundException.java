package com.devcourse.springbootboardjpa.common.exception.user;

import com.devcourse.springbootboardjpa.common.exception.BusinessException;
import com.devcourse.springbootboardjpa.common.exception.ErrorCode;

public class UserNotFoundException extends BusinessException {

    public UserNotFoundException() {
        super(ErrorCode.USER_NOT_FOUND);
    }
}
