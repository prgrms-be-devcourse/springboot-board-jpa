package com.example.jpaboard.post.service.dto;

import com.example.jpaboard.post.domain.Post;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.stream.Collectors;

public record PostResponses(List<PostResponse> postResponse) {

    public static PostResponses of(Page<Post> posts){
        List<PostResponse> responses = posts.stream()
                .map(PostResponse::new)
                .collect(Collectors.toList());
        return new PostResponses(responses);
    }

}
