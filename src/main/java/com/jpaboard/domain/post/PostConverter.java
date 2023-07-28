package com.jpaboard.domain.post;

import com.jpaboard.domain.post.dto.PostCreateRequest;
import com.jpaboard.domain.post.dto.PostResponse;
import com.jpaboard.domain.user.User;

public class PostConverter {

    private PostConverter() {
    }

    public static Post convertRequestToEntity(PostCreateRequest request, User user) {
        return Post.builder()
                .title(request.title())
                .content(request.content())
                .user(user)
                .build();
    }

    public static PostResponse convertEntityToResponse(Post post) {
        return PostResponse.builder()
                .id(post.getId())
                .title(post.getTitle())
                .content(post.getContent())
                .create_at(post.getCreateAt())
                .update_at(post.getUpdateAt())
                .build();
    }
}
