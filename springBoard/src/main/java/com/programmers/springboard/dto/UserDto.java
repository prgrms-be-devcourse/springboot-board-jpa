package com.programmers.springboard.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserDto {
    private String name;
    private int age;
    private String hobby;
}

