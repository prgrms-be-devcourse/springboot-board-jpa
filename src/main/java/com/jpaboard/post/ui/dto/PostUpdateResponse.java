package com.jpaboard.post.ui.dto;

import lombok.Builder;

@Builder
public record PostUpdateResponse(
        String title, String content
) {
}
