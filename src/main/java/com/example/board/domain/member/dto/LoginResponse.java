package com.example.board.domain.member.dto;

public record LoginResponse(
        String accessToken, String refreshToken, String email, Long id
)  {
}
