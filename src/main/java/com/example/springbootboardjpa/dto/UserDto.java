package com.example.springbootboardjpa.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;


public class UserDto {
    @Builder
    @Getter
    public static class Info {
        @NotNull
        private Long id;
        @NotNull
        private String name;
        @NotNull
        private Integer age;
    }
}
