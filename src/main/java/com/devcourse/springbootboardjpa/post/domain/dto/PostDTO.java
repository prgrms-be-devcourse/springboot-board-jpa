package com.devcourse.springbootboardjpa.post.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

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

        @Length(min = 1, max = 100)
        @NotBlank
        private String title;

        private String content;

        @NotNull
        private Long userId;
    }

    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class UpdateRequest {

        @Length(min = 1, max = 100)
        @NotBlank
        private String title;

        private String content;
    }
}
