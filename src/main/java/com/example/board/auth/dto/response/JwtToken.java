package com.example.board.auth.dto.response;

import lombok.*;

@Builder
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class JwtToken {
    private String grantType;
    private String accessToken;
    private String refreshToken;
}
