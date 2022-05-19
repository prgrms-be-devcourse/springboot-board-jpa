package com.example.spring_jpa_post.post.service;

import com.example.spring_jpa_post.post.dto.request.CreatePostRequest;
import com.example.spring_jpa_post.post.dto.response.FoundPostResponse;
import com.example.spring_jpa_post.post.entity.Post;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PostMapper {

    public Post toPostFromCreatePostRequest(CreatePostRequest createPostRequest) {
        return Post.builder()
                .title(createPostRequest.getTitle())
                .content(createPostRequest.getContent())
                .build();
    }

    public FoundPostResponse toFoundResponseFromPost(Post post) {
        return FoundPostResponse.builder()
                .id(post.getId())
                .title(post.getTitle())
                .content(post.getContent())
                .build();
    }
}
