package com.programmers.springbootboardjpa.dto.user;

import com.programmers.springbootboardjpa.global.enums.Hobby;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;

@Getter
public class UserResponse {

    private final Long id;

    private final String name;

    private final int age;

    private final Hobby hobby;

    private final String createdBy;

    private final LocalDateTime createdAt;

    @Builder
    public UserResponse(Long id, String name, int age, Hobby hobby, String createdBy, LocalDateTime createdAt) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.hobby = hobby;
        this.createdBy = createdBy;
        this.createdAt = createdAt;
    }
}
