package com.programmers.jpaboard.member.controller.dto;

import lombok.Builder;

import java.util.List;

@Builder
public class MemberResponseDto {
    private final String name;
    private final int age;
    private final List<String> hobbies;
}
