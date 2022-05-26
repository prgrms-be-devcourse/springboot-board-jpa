package com.example.springbootboardjpa.user.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
public class UserResponse {
    private UUID id;
    private int age;
    private String name;
    private String hobby;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @Builder
    public UserResponse(UUID id, int age, String name, String hobby,
                        LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.age = age;
        this.name = name;
        this.hobby = hobby;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }
}
