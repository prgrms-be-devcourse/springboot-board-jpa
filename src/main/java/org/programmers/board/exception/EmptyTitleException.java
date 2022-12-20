package org.programmers.board.exception;

public class EmptyTitleException extends RuntimeException {
    public EmptyTitleException(String message) {
        super(message);
    }
}