package com.jpaboard.domain.user.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UserCreationRequest(
    @NotBlank
    @Size(min = 1, max = 10)
    String name,
    int age,
    String hobby
) {
}
