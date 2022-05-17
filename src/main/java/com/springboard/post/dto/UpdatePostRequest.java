package com.springboard.post.dto;

public record UpdatePostRequest(
    String title,
    String content
) {}