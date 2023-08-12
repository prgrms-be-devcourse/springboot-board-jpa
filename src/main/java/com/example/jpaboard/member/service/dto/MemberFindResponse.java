package com.example.jpaboard.member.service.dto;

import com.example.jpaboard.member.domain.Age;
import com.example.jpaboard.member.domain.Member;
import com.example.jpaboard.member.domain.Name;

public class MemberFindResponse {

    private final Long id;
    private final Name name;
    private final Age age;
    private final String hobby;

    public MemberFindResponse(Long id, Name name, Age age, String hobby) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.hobby = hobby;
    }

    public MemberFindResponse(Member member) {
        this(member.getId(), member.getName(), member.getAge(), member.getHobby());
    }

    public Long getId() {
        return id;
    }

    public Name getName() {
        return name;
    }

    public Age getAge() {
        return age;
    }

    public String getHobby() {
        return hobby;
    }

}
