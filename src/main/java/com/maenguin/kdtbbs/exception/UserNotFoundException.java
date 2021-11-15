package com.maenguin.kdtbbs.exception;

public class UserNotFoundException extends NotFoundException {

    public UserNotFoundException(String message) {
        super(ErrorCode.USER_NOT_FOUND, message);
    }
}
