package com.programmers.board.dto.request;

import lombok.Getter;

@Getter
public class PostsGetRequest extends PageRequest {
    public PostsGetRequest(int page, int size) {
        super(page, size);
    }
}
