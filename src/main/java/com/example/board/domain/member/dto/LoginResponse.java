package com.example.board.domain.member.dto;

public record LoginResponse(
    String username,
    String accessToken
) {
    public static LoginResponse from(String username, String accessToken) {
        return new LoginResponse(username, accessToken);
    }
}
