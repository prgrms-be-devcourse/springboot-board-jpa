package com.example.board.dto;

import com.example.board.model.Post;

public record PostResponseDto(
        String userName,
        String title
) {

    public static PostResponseDto from(Post post) {
        return new PostResponseDto(post.getTitle(), post.getUser().getName());
    }


}
