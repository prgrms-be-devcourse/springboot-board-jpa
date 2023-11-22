package com.example.board.converter;

import com.example.board.domain.Post;
import com.example.board.domain.User;
import com.example.board.dto.request.post.CreatePostRequest;
import com.example.board.dto.response.PostResponse;

public class PostConverter {

    public static Post toPost(CreatePostRequest request, User author) {
        return Post.builder()
                .title(request.title())
                .content(request.content())
                .author(author)
                .build();
    }

    public static PostResponse toPostResponse(Post post) {
        return PostResponse.builder()
                .id(post.getId())
                .title(post.getTitle())
                .content(post.getContent())
                .createdAt(post.getCreatedAt())
                .author(UserConverter.toUserResponse(post.getAuthor()))
                .build();
    }
}
