package kdt.springbootboardjpa.controller.response;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import kdt.springbootboardjpa.respository.entity.Post;
import lombok.Builder;

public record PostResponse(@Positive Long id, @NotBlank String title, @NotBlank String content, @Positive Long createdBy) {

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
