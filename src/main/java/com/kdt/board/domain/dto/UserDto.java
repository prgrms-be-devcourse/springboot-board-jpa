package com.kdt.board.domain.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

public class UserDto {
    public record SaveRequest(
            @NotEmpty @Size(max = 20)
            String name,

            @NotEmpty
            int age,

            @NotEmpty
            @Size(max = 100)
            String hobby
    ) {
    }

    public record UpdateRequest(
            @NotNull
            Long id,

            @NotEmpty
            @Size(max = 20)
            String name,

            @NotEmpty
            int age,

            @NotEmpty
            @Size(max = 100)
            String hobby
    ) {
    }

    public record Response(
            Long id,
            String name,
            int age,
            String hobby,
            LocalDateTime createdAt,
            LocalDateTime updatedAt
    ) {
    }
}
