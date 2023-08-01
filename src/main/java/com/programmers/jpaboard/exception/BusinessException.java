package com.programmers.jpaboard.exception;

import java.util.Map;
import lombok.Getter;

@Getter
public class BusinessException extends RuntimeException {

    private final Map<String, Object> rejectedValuesMap;

    public BusinessException(String message, Map<String, Object> rejectedValuesMap) {
        super(message);
        this.rejectedValuesMap = rejectedValuesMap;
    }
}
