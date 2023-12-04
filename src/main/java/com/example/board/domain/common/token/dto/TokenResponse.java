package com.example.board.domain.common.token.dto;


public record TokenResponse(
    String accessToken,
    String refreshToken
) {
    public TokenResponse(String accessToken, String refreshToken) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }
}
