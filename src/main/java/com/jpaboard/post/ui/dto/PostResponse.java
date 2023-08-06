package com.jpaboard.post.ui.dto;

import com.jpaboard.user.ui.dto.UserResponse;
import lombok.Builder;

@Builder
public record PostResponse(
        String title, String content, UserResponse user
) {
}
