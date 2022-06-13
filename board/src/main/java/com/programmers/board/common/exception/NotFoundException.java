package com.programmers.board.common.exception;

public class NotFoundException extends RuntimeException{

    private String message;

    public NotFoundException(){}

    @Override
    public String getMessage() {
        return message;
    }
}
