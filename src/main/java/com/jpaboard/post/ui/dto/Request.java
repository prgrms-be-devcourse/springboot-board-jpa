package com.jpaboard.post.ui.dto;

import com.jpaboard.user.ui.dto.UserDto;

public record Request(
        String title, String content, UserDto.Request user
) implements PostDto {
}
