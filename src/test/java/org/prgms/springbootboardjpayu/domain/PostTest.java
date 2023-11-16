package org.prgms.springbootboardjpayu.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class PostTest {

    @DisplayName("게시글을 생성할 수 있다.")
    @Test
    void createPost() {
        // given
        String title = "배고파요";
        String content = "뭐 먹지?";
        User user = User.builder()
                .name("예림")
                .build();

        // when
        Post created = Post.builder()
                .title(title)
                .content(content)
                .user(user)
                .build();

        // then
        assertThat(created.getCratedAt()).isNotNull();
        assertThat(created.getCreatedBy()).isEqualTo(user.getName());
        assertThat(created).extracting("title", "content")
                .containsExactly(title, content);
    }

}