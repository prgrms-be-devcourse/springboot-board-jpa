package com.prgrms.springbootboardjpa.post.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class PostResponse {
    private final Long id;
    private final String title;
    private final String content;
    private final String userNickName;
}
