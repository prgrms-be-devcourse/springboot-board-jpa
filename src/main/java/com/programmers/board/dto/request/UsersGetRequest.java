package com.programmers.board.dto.request;

import lombok.Getter;

@Getter
public class UsersGetRequest {
    private final int page;
    private final int size;

    public UsersGetRequest(int page, int size) {
        this.page = page;
        this.size = size;
    }
}
