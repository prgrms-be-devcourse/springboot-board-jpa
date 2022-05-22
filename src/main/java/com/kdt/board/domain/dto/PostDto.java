package com.kdt.board.domain.dto;

import java.time.LocalDateTime;

public class PostDto {
    public record SaveRequest(
            String title,
            String content,
            Long userId
    ) {
    }

    public record UpdateRequest(
            Long id,
            String title,
            String content,
            Long userId
    ) {
    }

    public record CheckingIdResponse(
            Long id
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
