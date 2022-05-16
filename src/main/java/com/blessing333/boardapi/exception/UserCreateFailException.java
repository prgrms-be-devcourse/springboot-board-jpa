package com.blessing333.boardapi.exception;

public class UserCreateFailException extends RuntimeException{
    public UserCreateFailException(String message) {
        super(message);
    }
}
