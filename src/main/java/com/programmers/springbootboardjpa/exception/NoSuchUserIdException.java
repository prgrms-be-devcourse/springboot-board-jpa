package com.programmers.springbootboardjpa.exception;

public class NoSuchUserIdException extends RuntimeException{
    public NoSuchUserIdException() {
        super("There is no such user id.");
    }

    public NoSuchUserIdException(String message) {
        super(message);
    }

    public NoSuchUserIdException(String message, Throwable cause) {
        super(message, cause);
    }

    public NoSuchUserIdException(Throwable cause) {
        super(cause);
    }
}
