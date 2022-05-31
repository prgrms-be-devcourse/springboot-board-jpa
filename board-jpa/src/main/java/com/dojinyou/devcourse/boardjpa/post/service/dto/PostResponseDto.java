package com.dojinyou.devcourse.boardjpa.post.service.dto;

import com.dojinyou.devcourse.boardjpa.post.entity.Post;

import java.time.LocalDateTime;

public class PostResponseDto {
    private final long id;
    private final String title;
    private final String content;
    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;

    public long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public static PostResponseDto from(Post post) {
        return new PostResponseDto(post);
    }

    private PostResponseDto(Post post) {
        this.id = post.getId();
        this.title = post.getTitle();
        this.content = post.getContent();
        this.createdAt = post.getCreatedAt();
        this.updatedAt = post.getUpdatedAt();
    }
}
