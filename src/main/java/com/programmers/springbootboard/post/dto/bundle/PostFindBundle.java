package com.programmers.springbootboard.post.dto.bundle;

import com.programmers.springbootboard.annotation.ThreadSafety;
import lombok.Builder;

@ThreadSafety
@Builder
public class PostFindBundle {
    private final Long postId;

    public Long getPostId() {
        return postId;
    }
}
