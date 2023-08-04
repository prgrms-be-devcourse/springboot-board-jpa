package com.programmers.jpa_board.post.domain.dto.request;

public record CreatePostRequest(String title, String content, Long userId) {
}
