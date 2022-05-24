package com.prgrms.hyuk.exception;

public class AgeOutOfRangeException extends ClientException {

    private final ExceptionMessage exceptionMessage;

    public AgeOutOfRangeException(ExceptionMessage exceptionMessage) {
        this.exceptionMessage = exceptionMessage;
    }

    @Override
    public String getMessage() {
        return exceptionMessage.getMessage();
    }
}
