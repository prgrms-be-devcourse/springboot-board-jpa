package com.devcourse.springbootboardjpa.common.exception.post;

import com.devcourse.springbootboardjpa.common.exception.BusinessException;
import com.devcourse.springbootboardjpa.common.exception.ErrorCode;

public class PostNotFoundException extends BusinessException {

    public PostNotFoundException() {
        super(ErrorCode.POST_NOT_FOUND);
    }
}
