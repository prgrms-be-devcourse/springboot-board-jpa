package com.example.springbootboardjpa.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

public class PostDTO {

    @Getter
    @AllArgsConstructor
    public static class Save {
        @NotNull
        private String title;
        @NotNull
        private String content;
        @NotNull
        @Valid
        private UserDto.Info userDto;
    }

    @Getter
    @AllArgsConstructor
    public static class Request {
        @NotNull
        private Long id;
        @NotNull
        private String title;
        @NotNull
        private String content;
    }

    @Getter
    @Builder
    public static class Response {
        private Long id;
        private String title;
        private String content;
        private UserDto.Info userDto;
        private String createdBy;
        private LocalDateTime createdAt;
    }
}
