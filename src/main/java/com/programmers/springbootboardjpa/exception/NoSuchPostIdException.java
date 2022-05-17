package com.programmers.springbootboardjpa.exception;

public class NoSuchPostIdException extends RuntimeException{
    public NoSuchPostIdException() {
        super("There is no such post id.");
    }

    public NoSuchPostIdException(String message) {
        super(message);
    }

    public NoSuchPostIdException(String message, Throwable cause) {
        super(message, cause);
    }

    public NoSuchPostIdException(Throwable cause) {
        super(cause);
    }
}
