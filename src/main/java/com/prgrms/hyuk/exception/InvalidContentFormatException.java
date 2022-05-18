package com.prgrms.hyuk.exception;

public class InvalidContentFormatException extends RuntimeException {

    private final ExceptionMessage exceptionMessage;

    public InvalidContentFormatException(ExceptionMessage exceptionMessage) {
        this.exceptionMessage = exceptionMessage;
    }

    @Override
    public String getMessage() {
        return exceptionMessage.getMessage();
    }
}
