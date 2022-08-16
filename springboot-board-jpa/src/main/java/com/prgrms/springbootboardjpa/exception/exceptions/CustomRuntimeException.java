package com.prgrms.springbootboardjpa.exception.exceptions;

import lombok.Getter;
import org.springframework.http.HttpStatus;


public class CustomRuntimeException extends RuntimeException{
    private final CustomExceptionCode exceptionCode;

    public CustomRuntimeException(CustomExceptionCode exceptionCode) {
        this.exceptionCode = exceptionCode;
    }

    public CustomRuntimeException(CustomExceptionCode exceptionCode, String customMessage) {
        this.exceptionCode = exceptionCode;
        this.exceptionCode.setErrorMessage(customMessage);
    }

    public CustomExceptionCode getExceptionCode() {
        return exceptionCode;
    }
}
