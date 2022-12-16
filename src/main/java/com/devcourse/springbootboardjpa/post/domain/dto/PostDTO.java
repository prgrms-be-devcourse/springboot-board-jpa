package com.devcourse.springbootboardjpa.post.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class PostDTO {

    @Getter
    @Builder
    @AllArgsConstructor
    public static class FindResponse {
        private final Long id;
        private final String title;
        private final String content;
        private final Long userId;
        private final String userName;
    }

    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class SaveRequest {
        private String title;
        private String content;
        private Long userId;
    }

    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class UpdateRequest {
        private String title;
        private String content;
    }
}
