package com.programmers.springbootboardjpa.global.exception;

import com.programmers.springbootboardjpa.global.exception.rule.BusinessRule;

public class UserException extends BusinessException {

    public UserException(BusinessRule rule) {
        super(rule);
    }
    
}
