package org.programmers.springboardjpa.domain.post.dto;

import org.programmers.springboardjpa.domain.user.dto.UserDto.UserResponse;

public class PostResponse {
    public record PostResponseDto(
            Long id,
            String title,
            String content,
            UserResponse userDto
    ) {}
}
