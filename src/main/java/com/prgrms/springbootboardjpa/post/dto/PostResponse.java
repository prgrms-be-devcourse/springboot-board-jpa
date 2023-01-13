package com.prgrms.springbootboardjpa.post.dto;

import com.prgrms.springbootboardjpa.post.domain.Post;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class PostResponse {
    private long postId;
    private long createdBy;
    private String title;
    private String content;
    private LocalDateTime createdAt;

    public PostResponse() {
    }

    public PostResponse(Post post) {
        this.postId = post.getPostId();
        this.createdBy = post.getCreatedBy().getMemberId();
        this.title = post.getTitle();
        this.content = post.getContent();
        this.createdAt = post.getCreatedAt();
    }


}
