package dev.jpaboard.post.dto;

import dev.jpaboard.post.domain.Post;
import dev.jpaboard.user.domain.User;
import jakarta.validation.constraints.Size;

public record PostCreateRequest(
        @Size(max = 25) String title,
        @Size(max = 5000) String content
) {

    public static Post toPost(PostCreateRequest request, User user) {
        return Post.builder()
                .user(user)
                .title(request.title)
                .content(request.content())
                .build();
    }

}
