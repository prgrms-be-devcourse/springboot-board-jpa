package com.prgrms.jpa.controller.dto.post;

import lombok.Getter;

@Getter
public class PostIdResponse {
    private final Long id;

    public PostIdResponse(Long id) {
        this.id = id;
    }
}
