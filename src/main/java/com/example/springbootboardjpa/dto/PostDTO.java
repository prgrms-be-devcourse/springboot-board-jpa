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
        private Long userId;
    }

    @Getter
    @AllArgsConstructor
    public static class Request {
        @NotNull
        private String title;
        @NotNull
        private String content;
    }

    @Getter
    @Builder //  constructor level builder 알아보기
    public static class Response {
        private Long id;
        private String title;
        private String content;
        private String createdBy;
        private LocalDateTime createdAt;
    }

    @Getter // Response에는 getter를 해줘야한다...안그럼 Not Acceptable 예외 발생
    @AllArgsConstructor
    public static class ResponseId{
        private Long id;
    }
}
