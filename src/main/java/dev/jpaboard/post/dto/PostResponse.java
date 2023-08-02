package dev.jpaboard.post.dto;

import dev.jpaboard.post.domain.Post;
import lombok.Builder;

@Builder
public record PostResponse(String title, String content) {

    public static PostResponse from(Post post) {
        return PostResponse.builder()
                .title(post.getTitle())
                .content(post.getContent())
                .build();
    }

}
