package com.example.board.dto.request.post;

import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record PostSearchCondition(
        @NotNull
        LocalDateTime createdAtFrom,

        @NotNull
        LocalDateTime createdAtTo) {
}
