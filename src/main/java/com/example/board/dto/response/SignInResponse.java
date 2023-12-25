package com.example.board.dto.response;

import lombok.Builder;

@Builder
public record SignInResponse(String accessToken, String refreshToken) {
}
