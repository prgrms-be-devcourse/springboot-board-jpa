package com.spring.board.springboard.domain;

public class Response<T> {
    private final T data;

    public Response(T data) {
        this.data = data;
    }

    public T getData() {
        return data;
    }
}
