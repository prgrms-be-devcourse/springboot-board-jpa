package com.example.jpaboard.member.service.dto;

import com.example.jpaboard.member.domain.Age;
import com.example.jpaboard.member.domain.Member;

public class FindMemberResponse {

    private final Long id;
    private final String name;
    private final Age age;
    private final String hobby;

    public FindMemberResponse(Long id, String name, Age age, String hobby) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.hobby = hobby;
    }

    public FindMemberResponse(Member member) {
        this(member.getId(), member.getName(), member.getAge(), member.getHobby());
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Age getAge() {
        return age;
    }

    public String getHobby() {
        return hobby;
    }

}
