package com.kdt.springbootboardjpa.member.service.dto;

import com.kdt.springbootboardjpa.member.domain.Hobby;
import lombok.Builder;
import lombok.Getter;

@Getter
public class MemberResponse {

    private Long id;
    private String name;
    private int age;
    private Hobby hobby;

    @Builder
    public MemberResponse(Long id, String name, int age, Hobby hobby) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.hobby = hobby;
    }
}