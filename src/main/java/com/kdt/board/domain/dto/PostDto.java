package com.kdt.board.domain.dto;

import lombok.Builder;

import java.time.LocalDateTime;

public class PostDto {
    public record Save(
            String title,
            String content,
            Long userId
    ) {
    }

    public record Update(
            Long id,
            String title,
            String content,
            Long userId
    ) {
    }

    public record Response(
            Long id,
            String title,
            String content,
            UserDto.Response user,
            LocalDateTime createdAt,
            LocalDateTime updatedAt,
            String createdBy
    ) {
    }
}
