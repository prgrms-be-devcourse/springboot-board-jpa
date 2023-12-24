package com.example.board.domain.member.dto;

import com.example.board.domain.member.entity.Member;

public record MemberResponse(
    Long id,
    String email,
    String name,
    int age,
    String hobby
){
    public static MemberResponse from(Member member) {
        return new MemberResponse(
            member.getId(),
            member.getEmail(),
            member.getName(),
            member.getAge(),
            member.getHobby()
        );
    }
}
