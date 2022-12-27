package com.spring.board.springboard.user.domain;

public record MemberResponseDto(String name, Integer age, Hobby hobby) {

    public MemberResponseDto(Member member) {
        this(
                member.getName(),
                member.getAge(),
                member.getHobby()
        );
    }
}