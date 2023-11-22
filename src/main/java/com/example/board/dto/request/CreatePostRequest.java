package com.example.board.dto.request;

import jakarta.validation.constraints.NotBlank;

public record CreatePostRequest(@NotBlank String title, String content, Long authorId) {
}
