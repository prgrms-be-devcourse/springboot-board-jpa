package com.kdt.board.domain.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

public class PostDto {
    public record SaveRequest(
            @NotNull
            String title,
            @NotNull
            String content,
            Long userId
    ) {
    }

    public record UpdateRequest(
            @NotNull
            Long id,
            @NotNull
            String title,
            @NotNull
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
