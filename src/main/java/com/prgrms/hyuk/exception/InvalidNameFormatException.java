package com.prgrms.hyuk.exception;

public class InvalidNameFormatException extends RuntimeException {

    private final ExceptionMessage exceptionMessage;

    public InvalidNameFormatException(ExceptionMessage exceptionMessage) {
        this.exceptionMessage = exceptionMessage;
    }

    @Override
    public String getMessage() {
        return exceptionMessage.getMessage();
    }
}
