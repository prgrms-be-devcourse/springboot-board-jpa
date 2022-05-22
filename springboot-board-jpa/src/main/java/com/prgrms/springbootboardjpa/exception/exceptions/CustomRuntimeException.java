package com.prgrms.springbootboardjpa.exception.exceptions;

import lombok.Getter;

@Getter
public abstract class CustomRuntimeException extends RuntimeException{
    private int statusCode;
    private String errorDetails;

    public CustomRuntimeException(int statusCode, String errorDetails){
        this.statusCode = statusCode;
        this.errorDetails = errorDetails;
    }
}
