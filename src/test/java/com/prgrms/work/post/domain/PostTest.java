package com.prgrms.work.post.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import com.prgrms.work.user.domain.User;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

class PostTest {

    private static final User USER = User.create("김형욱", 27, "산책");
    private static final String TITLE = "제목";
    private static final String MODIFY_TITLE = "제목";
    private static final String CONTENT = "내용";
    private static final String MODIFY_CONTENT = "내용";

    @Test
    void 객체_생성() {
        Post post = Post.create("김형욱", "제목", "내용", USER);

        assertThat(post.getTitle()).isEqualTo(TITLE);
        assertThat(post.getContent()).isEqualTo(CONTENT);
    }

    @Test
    void 객체_수정() {
        Post post = Post.create("김형욱", "제목", "내용", USER);
        post.modify(MODIFY_TITLE, MODIFY_CONTENT);

        assertThat(post.getTitle()).isEqualTo(MODIFY_TITLE);
        assertThat(post.getContent()).isEqualTo(MODIFY_CONTENT);
    }

}