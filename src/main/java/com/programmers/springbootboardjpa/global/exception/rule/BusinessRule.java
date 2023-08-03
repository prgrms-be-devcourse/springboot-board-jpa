package com.programmers.springbootboardjpa.global.exception.rule;

import org.springframework.http.HttpStatus;

public interface BusinessRule {
    
    String getMessage();

    HttpStatus getStatus();

}
