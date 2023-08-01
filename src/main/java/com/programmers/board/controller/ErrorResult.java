package com.programmers.board.controller;

import lombok.Getter;

@Getter
public class ErrorResult {
    private final int code;
    private final String message;

    public ErrorResult(int code, String message) {
        this.code = code;
        this.message = message;
    }
}
