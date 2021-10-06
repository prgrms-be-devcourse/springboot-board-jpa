package com.programmers.jpaboard.member.controller.dto;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class MemberResponseDto {
    private final String name;
    private final int age;
    private final List<String> hobbies;
}
