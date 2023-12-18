package com.example.board.auth.dto.response;

public record TokenResponse(
        String grantType,
        String accessToken,
        String refreshToken
) {
}
