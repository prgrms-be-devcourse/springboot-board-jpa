package com.programmers.springbootboardjpa.global.exception;

import com.programmers.springbootboardjpa.global.exception.rule.BusinessRule;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class BusinessException extends RuntimeException {

    private final HttpStatus status;

    public BusinessException(BusinessRule rule) {
        super(rule.getMessage());
        this.status = rule.getStatus();
    }

}
