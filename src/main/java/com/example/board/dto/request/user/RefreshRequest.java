package com.example.board.dto.request.user;

import jakarta.validation.constraints.NotBlank;

public record RefreshRequest(
        @NotBlank
        String refreshToken
) {
}
