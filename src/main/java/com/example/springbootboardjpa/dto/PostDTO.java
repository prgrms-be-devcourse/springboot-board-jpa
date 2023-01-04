package com.example.springbootboardjpa.dto;

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
        private Long userId;
    }

    @Getter
    @AllArgsConstructor
    public static class Request {
        @NotNull
        private String title;
        @NotNull
        private String content;
        @NotNull
        private Long userId;
    }

    @Getter
    @Builder
    public static class Response {
        private Long id;
        private String title;
        private String content;
        private String createdBy;
        private LocalDateTime createdAt;
    }

    @Getter
    @AllArgsConstructor
    public static class ResponseId {
        private Long id;
    }
}
