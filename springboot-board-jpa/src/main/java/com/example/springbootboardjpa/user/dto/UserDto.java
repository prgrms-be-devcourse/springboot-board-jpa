package com.example.springbootboardjpa.user.dto;

import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserDto {
    private UUID id;
    private int age;
    private String name;
    private String hobby;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    @Builder
    public UserDto(UUID id, int age, String name, String hobby, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.age = age;
        this.name = name;
        this.hobby = hobby;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }
}
