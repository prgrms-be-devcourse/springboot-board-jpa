package com.kdt.board.global.exception;

public class NotValidException extends IllegalArgumentException {
    public NotValidException(String message) {
        super(message);
    }
}
