package com.programmers.board.controller.response;

import lombok.Getter;

@Getter
public class ErrorResult {
    private final String message;

    public ErrorResult(String message) {
        this.message = message;
    }
}
