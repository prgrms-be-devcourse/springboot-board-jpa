package com.prgrms.jpa.controller.dto.post;

import lombok.Getter;

@Getter
public class CreatePostResponse {
    private final Long id;

    public CreatePostResponse(Long id) {
        this.id = id;
    }
}
