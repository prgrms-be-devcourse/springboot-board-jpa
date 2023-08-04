package com.kdt.devcourse.module.post.presentation.dto;

import jakarta.validation.constraints.NotBlank;

import static com.kdt.devcourse.module.post.presentation.dto.PostDto.DefaultRequest;
import static com.kdt.devcourse.module.post.presentation.dto.PostDto.DefaultResponse;

public abstract sealed class PostDto permits DefaultRequest, DefaultResponse {
    public static final class DefaultRequest extends PostDto {
        public DefaultRequest(@NotBlank String title, @NotBlank String content) {
            super(title, content);
        }
    }

    public static final class DefaultResponse extends PostDto {
        private final Long id;

        public DefaultResponse(Long id, String title, String content) {
            super(title, content);
            this.id = id;
        }
    }

    private final String title;
    private final String content;

    public PostDto(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }
}
