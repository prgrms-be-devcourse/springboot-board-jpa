package com.maenguin.kdtbbs.exception;

public class PostNotFoundException extends NotFoundException {

    public PostNotFoundException(String message) {
        super(ErrorCode.POST_NOT_FOUND, message);
    }

}
