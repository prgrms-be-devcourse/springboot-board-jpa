package com.prgrms.jpa.controller.dto.post;

import lombok.Builder;
import lombok.Getter;

@Getter
public class GetByIdPostResponse {

    private final Long id;
    private final String title;
    private final String content;
    private final long userId;

    @Builder
    public GetByIdPostResponse(Long id, String title, String content, long userId) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.userId = userId;
    }
}
