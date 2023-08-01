package com.programmers.springbootboardjpa.global.exception;

import com.programmers.springbootboardjpa.global.exception.rule.BusinessRule;

public class PostException extends BusinessException {

    public PostException(BusinessRule rule) {
        super(rule);
    }

}
