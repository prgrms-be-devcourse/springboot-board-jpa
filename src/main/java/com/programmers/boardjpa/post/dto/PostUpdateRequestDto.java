package com.programmers.boardjpa.post.dto;

public record PostUpdateRequestDto (Long postId, String title, String content) {
}
