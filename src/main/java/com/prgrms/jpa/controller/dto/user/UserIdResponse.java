package com.prgrms.jpa.controller.dto.user;

import lombok.Getter;

@Getter
public class UserIdResponse {
    private final long id;

    public UserIdResponse(long id) {
        this.id = id;
    }
}
