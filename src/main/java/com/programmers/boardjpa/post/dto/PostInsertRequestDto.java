package com.programmers.boardjpa.post.dto;

public record PostInsertRequestDto (String title, String content, Long userId) {
}
