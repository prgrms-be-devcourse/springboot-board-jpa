package com.example.board.dto.response;

import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record PostResponse(Long id, String title, String content, LocalDateTime createdAt, UserResponse author) {
}
