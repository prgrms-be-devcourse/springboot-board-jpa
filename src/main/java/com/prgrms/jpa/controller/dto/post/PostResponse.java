package com.prgrms.jpa.controller.dto.post;

import com.prgrms.jpa.controller.dto.user.UserResponse;
import lombok.Builder;

public class PostResponse {

    private final Long id;
    private final String title;
    private final String content;
    private final UserResponse user;

    @Builder
    public PostResponse(Long id, String title, String content, UserResponse user) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.user = user;
    }
}
