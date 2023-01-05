package com.prgrms.board.exception;

public class IllegalModifyException extends RuntimeException{
    public IllegalModifyException() {
    }

    public IllegalModifyException(String message) {
        super(message);
    }
}
