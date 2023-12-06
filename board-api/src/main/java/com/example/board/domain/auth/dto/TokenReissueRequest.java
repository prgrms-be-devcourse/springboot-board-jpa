package com.example.board.domain.auth.dto;

public record TokenReissueRequest(
    String refreshToken
) {
}
