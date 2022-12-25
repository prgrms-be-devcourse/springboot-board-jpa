package kdt.springbootboardjpa.controller.response;

import kdt.springbootboardjpa.respository.entity.Post;
import lombok.Builder;

public record PostResponse(Long id, String title, String content, Long createdBy) {

    @Builder
    public PostResponse {
    }

    public static PostResponse toPostResponse(Post post) {
        return PostResponse.builder()
                .id(post.getId())
                .title(post.getTitle())
                .content(post.getContent())
                .createdBy(post.getUser().getId())
                .build();
    }
}
