package com.study.board.exception;

public abstract class BoardRuntimeException extends RuntimeException {

    public BoardRuntimeException() {
        super();
    }

    public BoardRuntimeException(String message) {
        super(message);
    }

    public BoardRuntimeException(String message, Throwable cause) {
        super(message, cause);
    }

    public BoardRuntimeException(Throwable cause) {
        super(cause);
    }

    protected BoardRuntimeException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
