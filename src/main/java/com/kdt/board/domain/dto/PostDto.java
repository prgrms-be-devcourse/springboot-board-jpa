package com.kdt.board.domain.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

public class PostDto {
    public record SaveRequest(
            @NotEmpty @Size(max = 100)
            String title,

            @NotEmpty
            String content,

            @NotNull
            Long userId
    ) {
    }

    public record UpdateRequest(
            @NotNull
            Long id,

            @NotEmpty
            @Size(max = 100)
            String title,

            @NotEmpty
            String content,

            @NotNull
            Long userId
    ) {
    }

    public record CheckingIdResponse(
            @NotNull
            Long id
    ) {
    }

    public record Response(
            Long id,
            String title,
            String content,
            UserDto.Response user,
            LocalDateTime createdAt,
            LocalDateTime updatedAt
    ) {
    }
}
