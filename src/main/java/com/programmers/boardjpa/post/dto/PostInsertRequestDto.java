package com.programmers.boardjpa.post.dto;

public record PostInsertRequestDto (Long postId, String title, String content, Long userId) {
}
