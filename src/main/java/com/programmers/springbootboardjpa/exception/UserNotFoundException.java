package com.programmers.springbootboardjpa.exception;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException() {
        super(ErrorMsg.USER_NOT_FOUND.getMessage());
    }
}
