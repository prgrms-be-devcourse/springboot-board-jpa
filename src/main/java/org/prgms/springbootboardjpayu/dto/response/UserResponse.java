package org.prgms.springbootboardjpayu.dto.response;

import lombok.Builder;

import java.time.LocalDateTime;

public record UserResponse(Long id, String name, Integer age, String hobby, LocalDateTime createdAt) {

    @Builder
    public UserResponse(Long id, String name, Integer age, String hobby, LocalDateTime createdAt) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.hobby = hobby;
        this.createdAt = createdAt;
    }
}
