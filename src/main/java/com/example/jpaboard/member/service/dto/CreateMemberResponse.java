package com.example.jpaboard.member.service.dto;

import com.example.jpaboard.member.domain.Age;
import com.example.jpaboard.member.domain.Member;

public record CreateMemberResponse(String name, Age age, String hobby) {

    public CreateMemberResponse(Member member) {
        this(member.getName(),
                member.getAge(),
                member.getHobby());
    }

}

