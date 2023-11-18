package com.programmers.springbootboardjpa.dto;

import com.programmers.springbootboardjpa.entity.Post;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class PostServiceResponseDto {
    private final Long postId;
    private final String title;
    private final String content;
    private final UserDto user;
    private final LocalDateTime createdAt;
    private final String createdBy;

    private PostServiceResponseDto(Long postId, String title, String content, UserDto user, LocalDateTime createdAt, String createdBy) {
        this.postId = postId;
        this.title = title;
        this.content = content;
        this.user = user;
        this.createdAt = createdAt;
        this.createdBy = createdBy;
    }

    public static PostServiceResponseDto of(Post post) {
        return new PostServiceResponseDto(post.getId(),
                post.getTitle(),
                post.getContent(),
                UserDto.of(post.getUser()),
                post.getCreatedAt(),
                post.getCreatedBy());
    }
}
