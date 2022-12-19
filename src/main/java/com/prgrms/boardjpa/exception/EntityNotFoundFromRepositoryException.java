package com.prgrms.boardjpa.exception;

public class EntityNotFoundFromRepositoryException extends RuntimeException {
    public EntityNotFoundFromRepositoryException() {
    }

    public EntityNotFoundFromRepositoryException(String message) {
        super(message);
    }

    public EntityNotFoundFromRepositoryException(String message, Throwable cause) {
        super(message, cause);
    }

    public EntityNotFoundFromRepositoryException(Throwable cause) {
        super(cause);
    }

    public EntityNotFoundFromRepositoryException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
