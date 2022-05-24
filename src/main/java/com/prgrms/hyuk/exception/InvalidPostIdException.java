package com.prgrms.hyuk.exception;

public class InvalidPostIdException extends ClientException {

    private final ExceptionMessage exceptionMessage;

    public InvalidPostIdException(ExceptionMessage exceptionMessage) {
        this.exceptionMessage = exceptionMessage;
    }

    @Override
    public String getMessage() {
        return exceptionMessage.getMessage();
    }
}
