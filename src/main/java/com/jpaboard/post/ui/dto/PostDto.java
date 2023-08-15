package com.jpaboard.post.ui.dto;

import com.jpaboard.user.ui.dto.UserDto;

public sealed interface PostDto permits PostDto.Request, PostDto.Response, PostDto.PostUpdateRequest {
    record Request(String title, String content, UserDto.Request user) implements PostDto {
    }

    record Response(String title, String content, UserDto.Response user) implements PostDto {
    }

    record PostUpdateRequest(String title, String content) implements PostDto {
    }
}
