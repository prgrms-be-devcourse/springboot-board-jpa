package com.example.board.dto.response;

import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record UserResponse(Long id, String name, Integer age, String hobby, LocalDateTime createdAt,
                           LocalDateTime updatedAt) {
}
