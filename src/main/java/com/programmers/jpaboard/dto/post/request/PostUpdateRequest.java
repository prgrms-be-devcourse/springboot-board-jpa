package com.programmers.jpaboard.dto.post.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PostUpdateRequest {

    private String title;
    private String content;
}
