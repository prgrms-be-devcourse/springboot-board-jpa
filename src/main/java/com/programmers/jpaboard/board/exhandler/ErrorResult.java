package com.programmers.jpaboard.board.exhandler;

import lombok.Getter;

@Getter
public class ErrorResult {
    private final String error;
    private final String details;

    public ErrorResult(String error, String details) {
        this.error = error;
        this.details = details;
    }
}
