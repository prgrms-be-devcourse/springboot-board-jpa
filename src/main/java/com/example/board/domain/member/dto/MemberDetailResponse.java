package com.example.board.domain.member.dto;

import com.example.board.domain.member.entity.Member;

public record MemberDetailResponse(
    Long id,
    String email,
    String name,
    int age,
    String hobby
){
    public static MemberDetailResponse from(Member member) {
        return new MemberDetailResponse(
            member.getId(),
            member.getEmail(),
            member.getName(),
            member.getAge(),
            member.getHobby()
        );
    }
}
