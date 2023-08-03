package com.prgrms.board.support;

import com.prgrms.board.domain.post.entity.Post;
import com.prgrms.board.domain.user.entity.User;

public class PostFixture {

    public static Post create(User user, String title, String content) {
        return Post.create(user, title, content);
    }

    public static Post createByBuilder(User user, String title, String content) {
        return Post.builder()
            .id(1L)
            .user(user)
            .title(title)
            .content(content)
            .build();
    }
}
