package com.example.board.domain.member.dto;

public record LoginRequest(
        String principal, String credentials
) {
}
