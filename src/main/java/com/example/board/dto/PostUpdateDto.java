package com.example.board.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

//TODO: userId 타입 통일
public record PostUpdateDto(
        @NotNull Long userId,
        @Size(max = 20) String title,
        @NotBlank String contents
) {
}
