package com.spring.board.springboard.user.domain.dto;

import com.spring.board.springboard.user.domain.Hobby;
import com.spring.board.springboard.user.domain.Member;

public record MemberDetailResponseDto(Integer memberId, String email, String password, String name, Integer age,
                                      Hobby hobby) {

    public MemberDetailResponseDto(Member member) {
        this(
                member.getId(),
                member.getEmail(),
                member.getPassword(),
                member.getName(),
                member.getAge(),
                member.getHobby()
        );
    }
}