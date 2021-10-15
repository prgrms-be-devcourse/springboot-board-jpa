package com.kdt.Board.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@NoArgsConstructor
@Getter
@Setter
public class UserResponse {

    private String name;
    private Integer age;
    private String hobby;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;

    @Builder
    public UserResponse(String name, Integer age, String hobby, LocalDateTime createdAt, LocalDateTime modifiedAt) {
        this.name = name;
        this.age = age;
        this.hobby = hobby;
        this.createdAt = createdAt;
        this.modifiedAt = modifiedAt;
    }
}
