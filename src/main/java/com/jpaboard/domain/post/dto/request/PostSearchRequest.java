package com.jpaboard.domain.post.dto.request;

import jakarta.annotation.Nullable;

public record PostSearchRequest(
        @Nullable
        String title,
        @Nullable
        String content,
        @Nullable
        String keyword
) {
}
