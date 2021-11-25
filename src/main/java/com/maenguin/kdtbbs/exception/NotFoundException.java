package com.maenguin.kdtbbs.exception;

public class NotFoundException extends BusinessException{

    public NotFoundException(ErrorCode errorCode, String message) {
        super(errorCode, message);
    }
}
