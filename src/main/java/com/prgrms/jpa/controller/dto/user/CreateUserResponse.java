package com.prgrms.jpa.controller.dto.user;

import lombok.Getter;

@Getter
public class CreateUserResponse {
    private final long id;

    public CreateUserResponse(long id) {
        this.id = id;
    }
}
