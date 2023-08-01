package com.programmers.springbootboardjpa.global.exception.rule;

import org.springframework.http.HttpStatus;

public interface BusinessRule {

    String message = null;
    HttpStatus status = null;

    String getMessage();

    HttpStatus getStatus();

}
