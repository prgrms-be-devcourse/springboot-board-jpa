package com.example.springbootboardjpa.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;


public class UserDto {
    @Builder
    @Getter
    public static class Info {
        @NotNull
        private long id;
        @NotNull
        private String name;
        @NotNull
        private int age;
        private String hobby;
        private String createdBy;
        private LocalDateTime createdAt;
    }
}
