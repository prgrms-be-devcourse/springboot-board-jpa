package com.kdt.devcourse.global.exception.common;

import com.kdt.devcourse.global.exception.CustomException;
import com.kdt.devcourse.global.exception.ErrorCode;

public class PostNotFoundException extends CustomException {
    private final Long id;

    public PostNotFoundException(ErrorCode errorCode, Long id) {
        super(errorCode);
        this.id = id;
    }

    @Override
    public String getMessage() {
        return super.getMessage() + id;
    }
}
