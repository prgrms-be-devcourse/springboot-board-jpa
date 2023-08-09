package com.jpaboard.post.ui.dto;

public record UpdateRequest(
        String title, String content
) implements PostDto {
}
