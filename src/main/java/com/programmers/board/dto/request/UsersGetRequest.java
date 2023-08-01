package com.programmers.board.dto.request;

import lombok.Getter;

@Getter
public class UsersGetRequest extends PageRequest {
    protected UsersGetRequest(int page, int size) {
        super(page, size);
    }
}
