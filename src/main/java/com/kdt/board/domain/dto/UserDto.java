package com.kdt.board.domain.dto;

import java.time.LocalDateTime;

public class UserDto {
    public record SaveRequest(
            String name,
            int age,
            String hobby
    ) {
    }

    public record UpdateRequest(
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
