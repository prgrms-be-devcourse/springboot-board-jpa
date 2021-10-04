package com.programmers.jpaboard.member.controller.dto;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
public class MemberCreationDto {
    private final String name;
    private final int age;
    private final List<String> hobbies;

    public MemberCreationDto(String name, int age, List<String> hobbies) {
        this.name = name;
        this.age = age;
        this.hobbies = hobbies;
    }
}
