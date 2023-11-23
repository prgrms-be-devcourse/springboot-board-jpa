package com.devcourse.springbootboardjpahi.dto;

import com.devcourse.springbootboardjpahi.domain.Post;
import com.devcourse.springbootboardjpahi.domain.User;
import java.time.LocalDateTime;
import lombok.Builder;

@Builder
public record PostResponse(
        long id,
        String title,
        String content,
        String authorName,
        LocalDateTime createdAt) {

    public static PostResponse from(Post post) {
        User author = post.getUser();

        return PostResponse.builder()
                .id(post.getId())
                .title(post.getTitle())
                .content(post.getContent())
                .authorName(author.getName())
                .createdAt(post.getCreatedAt())
                .build();
    }
}
