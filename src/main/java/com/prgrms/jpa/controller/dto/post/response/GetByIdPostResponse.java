package com.prgrms.jpa.controller.dto.post.response;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class GetByIdPostResponse {

    private Long id;
    private String title;
    private String content;
    private long userId;

    @Builder
    public GetByIdPostResponse(Long id, String title, String content, long userId) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.userId = userId;
    }
}
