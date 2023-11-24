package com.example.board.dto.request.post;

import jakarta.validation.constraints.NotNull;

public record DeletePostRequest(@NotNull Long authorId) {
}
