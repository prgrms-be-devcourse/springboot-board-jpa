package com.waterfogsw.springbootboardjpa.post.controller.dto;

public record PostResponse(
        String title,
        String content,
        String userName,
        String userEmail
) {
}
