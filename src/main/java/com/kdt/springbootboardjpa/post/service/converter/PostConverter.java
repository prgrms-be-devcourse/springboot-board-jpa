package com.kdt.springbootboardjpa.post.service.converter;

import com.kdt.springbootboardjpa.member.domain.Member;
import com.kdt.springbootboardjpa.post.domain.Post;
import com.kdt.springbootboardjpa.post.service.dto.CreatePostRequest;
import com.kdt.springbootboardjpa.post.service.dto.PostResponse;
import org.springframework.stereotype.Component;

@Component
public class PostConverter {

    public Post createRequestToPost(CreatePostRequest createPostRequest, Member member) {
        return Post.builder()
                .title(createPostRequest.getTitle())
                .content(createPostRequest.getContent())
                .member(member)
                .build();
    }

    public PostResponse postToResponse(Post post) {
        return PostResponse.builder()
                .id(post.getId())
                .title(post.getTitle())
                .content(post.getContent())
                .memberName(post.getMember().getName())
                .build();
    }
}