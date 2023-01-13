package com.prgrms.springbootboardjpa.post.dto;

import com.prgrms.springbootboardjpa.post.domain.Post;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PostUpdateRequest {
    private long postId;
    private String title;
    private String content;

    public PostUpdateRequest() {
    }

    public PostUpdateRequest(long postId, String title, String content) {
        this.postId = postId;
        this.title = title;
        this.content = content;
    }

    public Post toPost() {
        return new Post(this.title, this.content);
    }
}
