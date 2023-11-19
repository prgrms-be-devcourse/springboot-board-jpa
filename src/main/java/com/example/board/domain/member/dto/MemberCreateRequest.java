package com.example.board.domain.member.dto;

import com.example.board.domain.member.entity.Member;

public record MemberCreateRequest(
    String email,
    String name,
    int age,
    String hobby
) {

    public Member toEntity() {
        return Member.builder()
            .email(email)
            .name(name)
            .age(age)
            .hobby(hobby)
            .build();
    }
}

