package com.example.board.domain.member.dto;

public record MemberResponse(
        String token, String email, Long id
)  {
}
