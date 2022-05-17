package com.example.boardjpa.dto;

public class CreatePostResponseDto {
    private final Long postId;

    public CreatePostResponseDto(Long postId) {
        this.postId = postId;
    }

    public Long getPostId() {
        return postId;
    }
}
