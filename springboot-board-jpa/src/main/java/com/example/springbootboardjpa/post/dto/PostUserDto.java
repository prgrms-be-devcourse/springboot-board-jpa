package com.example.springbootboardjpa.post.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class PostUserDto {
    private UUID id;
    private String name;

    @Builder
    public PostUserDto(UUID id, String name) {
        this.id = id;
        this.name = name;
    }
}
