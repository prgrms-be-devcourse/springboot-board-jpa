package org.programmers.board.exception;

public class EmptyContentException extends RuntimeException {
    public EmptyContentException(String message) {
        super(message);
    }
}