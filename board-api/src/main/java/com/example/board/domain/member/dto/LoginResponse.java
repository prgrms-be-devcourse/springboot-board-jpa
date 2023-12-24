package com.example.board.domain.member.dto;

public record LoginResponse(
    String username,
    String accessToken,
    String refreshToken
) {
    public static LoginResponse from(String username, String accessToken, String refreshToken) {
        return new LoginResponse(username, accessToken, refreshToken);
    }
}
