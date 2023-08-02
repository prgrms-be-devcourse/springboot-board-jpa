package com.example.board.domain.post.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record PostCreateRequest(@NotNull Long userId, @NotBlank String title,
                                @NotBlank String content) {

}