package com.example.boardjpa.dto;

import com.example.boardjpa.domain.User;

import java.time.LocalDateTime;

public class UserResponseDto {
    private final Long id;
    private final String name;
    private final Integer age;
    private final String hobby;
    private final LocalDateTime createdAt;
    private final String createdBy;

    public UserResponseDto(User entity) {
        this.id = entity.getId();
        this.name = entity.getName();
        this.age = entity.getAge();
        this.hobby = entity.getHobby();
        this.createdAt = entity.getCreatedAt();
        this.createdBy = entity.getCreatedBy();
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Integer getAge() {
        return age;
    }

    public String getHobby() {
        return hobby;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public String getCreatedBy() {
        return createdBy;
    }
}
