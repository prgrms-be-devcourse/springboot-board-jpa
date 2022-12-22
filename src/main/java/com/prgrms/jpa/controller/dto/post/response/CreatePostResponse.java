package com.prgrms.jpa.controller.dto.post.response;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CreatePostResponse {

    private Long id;

    @Builder
    public CreatePostResponse(Long id) {
        this.id = id;
    }
}
