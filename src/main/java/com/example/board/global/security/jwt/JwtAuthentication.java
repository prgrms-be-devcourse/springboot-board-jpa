package com.example.board.global.security.jwt;

public record JwtAuthentication(String token, String username) {    // 인증 완료 후 인증된 사용자를 표현하기 위함. 불변 객체
}
