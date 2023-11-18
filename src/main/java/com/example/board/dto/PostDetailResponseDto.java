package com.example.board.dto;

import com.example.board.model.Post;

public record PostDetailResponseDto(
        Long postId,
        Long userID,
        String userName,
        String title,
        String contents
) {

    public static PostDetailResponseDto from(Post post) {
        return new PostDetailResponseDto(
                post.getId(),
                post.getUser().getId(),
                post.getUser().getName(),
                post.getTitle(),
                post.getContents());
    }
}
