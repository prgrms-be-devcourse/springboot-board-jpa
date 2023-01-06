package com.prgrms.springbootboardjpa.post.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PostUpdateRequest {
    private long postId;
    private long createdBy;
    private String title;
    private String content;

    public PostUpdateRequest() {
    }

    public PostUpdateRequest(long postId, long createdBy, String title, String content) {
        this.postId = postId;
        this.createdBy = createdBy;
        this.title = title;
        this.content = content;
    }
}
