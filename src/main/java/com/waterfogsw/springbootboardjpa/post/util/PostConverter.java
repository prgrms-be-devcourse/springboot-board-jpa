package com.waterfogsw.springbootboardjpa.post.util;

import com.waterfogsw.springbootboardjpa.post.controller.dto.PostAddRequest;
import com.waterfogsw.springbootboardjpa.post.controller.dto.PostResponse;
import com.waterfogsw.springbootboardjpa.post.controller.dto.PostUpdateRequest;
import com.waterfogsw.springbootboardjpa.post.entity.Post;
import org.springframework.stereotype.Component;

@Component
public class PostConverter {
    public Post toEntity(PostAddRequest postAddRequest) {
        return Post.builder()
                .title(postAddRequest.title())
                .content(postAddRequest.content())
                .build();
    }

    public Post toEntity(PostUpdateRequest postUpdateRequest) {
        return Post.builder()
                .title(postUpdateRequest.title())
                .content(postUpdateRequest.content())
                .build();
    }

    public PostResponse toDto(Post post) {
        return new PostResponse(
                post.getTitle(),
                post.getContent(),
                post.getUser().getName(),
                post.getUser().getEmail()
        );
    }
}
