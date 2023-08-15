package com.jpaboard.post.ui;

import com.jpaboard.user.ui.dto.UserDto;

import static com.jpaboard.post.ui.PostCreate.Response;
import static com.jpaboard.post.ui.PostCreate.Request;

public sealed interface PostCreate permits Request, Response {
    record Request(String title, String content, UserDto.Request user) implements PostCreate {
    }

    record Response(Long id, String title, String content) implements PostCreate {
    }
}
