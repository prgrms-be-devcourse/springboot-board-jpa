package com.spring.board.springboard.user.domain.dto;

import com.spring.board.springboard.user.domain.Hobby;
import com.spring.board.springboard.user.domain.Member;

public record MemberResponseDto(Integer memberId, String email, String name, Integer age, Hobby hobby) {

    public MemberResponseDto(Member member) {
        this(
                member.getId(),
                member.getEmail(),
                member.getName(),
                member.getAge(),
                member.getHobby()
        );
    }
}