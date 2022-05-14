package org.programmers.board.exception;

public class TooLongNameException extends RuntimeException {
    public TooLongNameException(String message) {
        super(message);
    }
}