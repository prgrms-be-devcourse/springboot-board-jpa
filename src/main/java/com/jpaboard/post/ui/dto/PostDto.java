package com.jpaboard.post.ui.dto;

import com.jpaboard.user.ui.dto.UserDto;
import lombok.Builder;

public sealed interface PostDto permits Request, Response, UpdateRequest  {
    @Builder
    record Request(String title, String content, UserDto.Request user){ }

    @Builder
    record Response(String title, String content, UserDto.Response user){ }

    @Builder
    record PostUpdateRequest(String title, String content) { }

}
