package com.example.board.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserRequestDto {
    private String name;
    private int age;
    private String hobby;
}
