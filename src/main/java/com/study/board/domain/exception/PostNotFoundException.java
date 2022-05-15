package com.study.board.domain.exception;

import java.text.MessageFormat;

public class PostNotFoundException extends BoardRuntimeException {

    public PostNotFoundException(Long id) {
        super(MessageFormat.format("post of id : {0} not found", id));
    }

    public PostNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public PostNotFoundException(Throwable cause) {
        super(cause);
    }

    protected PostNotFoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
