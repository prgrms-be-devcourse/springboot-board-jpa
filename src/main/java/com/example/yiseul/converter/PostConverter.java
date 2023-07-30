package com.example.yiseul.converter;

import com.example.yiseul.domain.Member;
import com.example.yiseul.domain.Post;
import com.example.yiseul.dto.post.PostCreateRequestDto;
import com.example.yiseul.dto.post.PostResponseDto;

public class PostConverter {

    public static Post convertPost(Member member, PostCreateRequestDto createRequestDto){
        return Post.createPost(member, createRequestDto.title(), createRequestDto.content());
    }

    public static PostResponseDto convertPostDto(Post post){
        return new PostResponseDto(post.getId(), post.getTitle(), post.getContent(), post.getCreatedAt(), post.getCreatedBy());
    }
}
