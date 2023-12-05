package com.example.board.domain.auth.dto;


public record TokenResponse(
    String accessToken,
    String refreshToken
) {
    public TokenResponse(String accessToken, String refreshToken) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }
}
