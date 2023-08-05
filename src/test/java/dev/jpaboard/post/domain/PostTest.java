package dev.jpaboard.post.domain;

import dev.jpaboard.user.domain.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

class PostTest {

    private static final String 제목 = "제목";
    private static final String 내용 = "내용";

    @DisplayName("")
    @Test
    void updateTest() {
        User user = User.builder()
                .email("qkrdmswl1018@naver.com")
                .password("QWert123?")
                .name("name")
                .age(23)
                .hobby("hobby")
                .build();

        Post post = Post.builder()
                .title("title")
                .content("content")
                .user(user)
                .build();

        post.update(제목, 내용);

        assertAll(
                () -> assertThat(post.getTitle()).isEqualTo(제목),
                () -> assertThat(post.getContent()).isEqualTo(내용)
        );
    }

}
