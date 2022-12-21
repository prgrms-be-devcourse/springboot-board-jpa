package com.example.springbootboardjpa.dto;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class UserDto {
    private String name;
    private int age;
    private String hobby;
}
