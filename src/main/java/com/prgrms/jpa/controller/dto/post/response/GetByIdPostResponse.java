package com.prgrms.jpa.controller.dto.post.response;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
@Builder
public class GetByIdPostResponse {

    private final Long id;
    private final String title;
    private final String content;
    private final long userId;
}
