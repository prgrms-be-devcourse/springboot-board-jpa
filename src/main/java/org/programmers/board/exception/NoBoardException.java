package org.programmers.board.exception;

import java.util.function.Supplier;

public class NoBoardException extends RuntimeException {
    public NoBoardException(String message) {
        super(message);
    }


}