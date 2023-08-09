package com.jpaboard.post.ui.dto;


import com.jpaboard.user.ui.dto.UserDto;

public record Response(
        String title, String content, UserDto.Response user
) implements PostDto {
}
