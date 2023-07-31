package com.programmers.board.dto.request;

import lombok.Getter;

@Getter
public class PostsGetRequest {
    private final int page;
    private final int size;

    public PostsGetRequest(int page, int size) {
        this.page = page;
        this.size = size;
    }
}
