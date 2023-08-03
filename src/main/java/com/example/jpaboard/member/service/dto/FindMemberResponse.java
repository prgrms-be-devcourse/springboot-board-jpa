package com.example.jpaboard.member.service.dto;

import com.example.jpaboard.member.domain.Age;
import com.example.jpaboard.member.domain.Member;
import lombok.Getter;

@Getter
public class FindMemberResponse {

    private final String name;
    private final Age age;
    private final String hobby;

    public FindMemberResponse(String name, Age age, String hobby) {
        this.name = name;
        this.age = age;
        this.hobby = hobby;
    }
    public FindMemberResponse (Member member) {
        this(member.getName(), member.getAge(), member.getHobby());
    }

}
