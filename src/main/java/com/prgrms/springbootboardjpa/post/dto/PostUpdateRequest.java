package com.prgrms.springbootboardjpa.post.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PostUpdateRequest {
    private long postId;
    private String title;
    private String content;

    public PostUpdateRequest(long postId, String title, String content) {
        this.postId = postId;
        this.title = title;
        this.content = content;
    }
}
