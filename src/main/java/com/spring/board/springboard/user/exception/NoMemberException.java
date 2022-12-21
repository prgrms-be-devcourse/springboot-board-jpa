package com.spring.board.springboard.user.exception;

public class NoMemberException extends RuntimeException{
    public NoMemberException(String message) {
        super(message);
    }
}
