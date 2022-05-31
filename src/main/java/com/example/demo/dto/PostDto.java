package com.example.demo.dto;

import lombok.Builder;

import java.time.LocalDateTime;


public record PostDto(Long id, String title, String content, LocalDateTime updatedAt, UserDto userDto) {

    @Builder
    public PostDto{

    }

}
