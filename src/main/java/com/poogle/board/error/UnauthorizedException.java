package com.poogle.board.error;

import com.poogle.board.util.MessageUtils;

public class UnauthorizedException extends ServiceRuntimeException {

    private static final String MESSAGE_KEY = "error.auth";
    private static final String MESSAGE_DETAIL = "error.auth.details";

    public UnauthorizedException(String message) {
        super(MESSAGE_KEY, MESSAGE_DETAIL, new Object[]{message});
    }

    @Override
    public String getMessage() {
        return MessageUtils.getMessage(getDetailKey(), getParams());
    }

    @Override
    public String toString() {
        return MessageUtils.getMessage(getMessageKey());
    }
}
