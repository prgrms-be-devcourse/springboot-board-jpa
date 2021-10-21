package com.kdt.programmers.forum.transfer;

import com.kdt.programmers.forum.domain.Post;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class PostDto {

    private final Long id;
    private final String title;
    private final String content;
    private final LocalDateTime createdAt;

    public PostDto(Post post) {
        this.id = post.getId();
        this.title = post.getTitle();
        this.content = post.getContent();
        this.createdAt = post.getCreatedAt();
    }
}
