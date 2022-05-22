package com.kdt.board.domain.dto;

import java.time.LocalDateTime;

public class UserDto {
    public record Save(
            String name,
            int age,
            String hobby
    ) {
    }

    public record Update(
            Long id,
            String name,
            int age,
            String hobby
    ) {
    }

    public record Response(
            Long id,
            String name,
            int age,
            String hobby,
            LocalDateTime createdAt,
            LocalDateTime updatedAt,
            String createdBy
    ) {
    }
}
