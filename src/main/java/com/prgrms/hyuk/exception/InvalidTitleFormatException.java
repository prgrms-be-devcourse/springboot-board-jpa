package com.prgrms.hyuk.exception;

public class InvalidTitleFormatException extends ClientException {

    private final ExceptionMessage exceptionMessage;

    public InvalidTitleFormatException(ExceptionMessage exceptionMessage) {
        this.exceptionMessage = exceptionMessage;
    }

    @Override
    public String getMessage() {
        return exceptionMessage.getMessage();
    }
}
