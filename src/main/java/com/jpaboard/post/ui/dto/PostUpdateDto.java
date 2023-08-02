package com.jpaboard.post.ui.dto;

import lombok.Builder;

@Builder
public record PostUpdateDto(
        String title, String content
) {
}
