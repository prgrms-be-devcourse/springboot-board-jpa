package com.example.yiseul.dto.member;

public record MemberResponseDto(
    Long memberId,
    String name,
    Integer age,
    String hobby,
    String createdAt,
    String createdBy
) {
}
