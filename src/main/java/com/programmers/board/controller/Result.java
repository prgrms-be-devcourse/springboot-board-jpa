package com.programmers.board.controller;

import lombok.Getter;

@Getter
public class Result<T> {
    private final T data;

    public Result(T data) {
        this.data = data;
    }
}
