package com.example.board.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;

@Builder
public record CreateUserRequest(@NotBlank String name, @Min(1) Integer age, @NotBlank String hobby) {
}
