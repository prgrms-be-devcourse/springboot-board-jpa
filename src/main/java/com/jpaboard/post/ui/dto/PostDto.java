package com.jpaboard.post.ui.dto;

import com.jpaboard.user.ui.dto.UserDto;
import lombok.Builder;

@Builder
public record PostDto(
        String title, String content, UserDto user
) {
}
