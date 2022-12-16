package com.devcourse.springbootboardjpa.post.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

public class PostDTO {

    @Getter
    @Builder
    @AllArgsConstructor
    public static class FindResponse {
        private final Long id;
        private final String title;
        private final String content;
    }
}
