package com.jpaboard.post.ui;

import com.jpaboard.user.ui.dto.UserDto;

import static com.jpaboard.post.ui.PostCommon.*;

public sealed interface PostCommon permits Response {
    record Response(Long id, String title, String content, UserDto.Response user) implements PostCommon {
    }

}
