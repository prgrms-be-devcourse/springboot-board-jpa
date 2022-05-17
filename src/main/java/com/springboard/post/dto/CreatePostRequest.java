package com.springboard.post.dto;

public record CreatePostRequest(
    Long userId,
    String title,
    String content
) {}