package com.programmers.springbootboard.post.dto.response;

import com.programmers.springbootboard.annotation.ThreadSafety;
import lombok.Builder;

@ThreadSafety
@Builder
public class PostDeleteResponse {
    private final Long postId;
    private final String email;

    public Long getPostId() {
        return postId;
    }

    public String getEmail() {
        return email;
    }
}
