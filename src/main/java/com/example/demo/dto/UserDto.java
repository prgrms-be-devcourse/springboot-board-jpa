package com.example.demo.dto;

import lombok.Builder;

public record UserDto (Long id, String name, int age, String hobby) {

    @Builder
    public UserDto {
    }
}

