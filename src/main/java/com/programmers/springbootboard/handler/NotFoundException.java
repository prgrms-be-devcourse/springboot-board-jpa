package com.programmers.springbootboard.handler;

public class NotFoundException extends IllegalArgumentException {
    public NotFoundException(String msg) {
        super(msg);
    }
}
