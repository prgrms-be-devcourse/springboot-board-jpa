package com.example.board.converter;

import com.example.board.domain.Post;
import com.example.board.dto.PostRequest;
import com.example.board.dto.PostResponse;
import org.springframework.stereotype.Component;

@Component
public class PostConverter {
    public Post convertToPost(PostRequest createPostRequest) {
        return Post.builder()
                .title(createPostRequest.getTitle())
                .content(createPostRequest.getContent())
                .build();
    }

    public PostResponse convertToResponse(Post post) {
        return PostResponse.builder()
                .id(post.getId())
                .title(post.getTitle())
                .content(post.getContent())
                .createdAt(post.getCreatedAt())
                .build();
    }
}
