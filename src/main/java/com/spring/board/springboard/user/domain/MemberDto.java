package com.spring.board.springboard.user.domain;

public record MemberDto (String name, Integer age, Hobby hobby){

    public MemberDto(Member member) {
        this(
                member.getName(),
                member.getAge(),
                member.getHobby()
        );
    }
}
