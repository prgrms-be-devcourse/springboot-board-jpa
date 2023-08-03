package com.prgrms.board.support;

import com.prgrms.board.domain.post.entity.Post;
import com.prgrms.board.domain.user.entity.User;

public class PostFixture {

    private Long id;
    private String title = "제목";
    private String content = "내용";
    private User user = UserFixture.user().build();

    private PostFixture() {
    }

    public static PostFixture post() {
        return new PostFixture();
    }

    public PostFixture id(Long id) {
        this.id = id;
        return this;
    }

    public PostFixture title(String title) {
        this.title = title;
        return this;
    }

    public PostFixture content(String content) {
        this.content = content;
        return this;
    }

    public PostFixture user(User user) {
        this.user = user;
        return this;
    }

    public Post build() {
        return Post.builder()
            .id(id)
            .user(user)
            .title(title)
            .content(content)
            .build();
    }
}
