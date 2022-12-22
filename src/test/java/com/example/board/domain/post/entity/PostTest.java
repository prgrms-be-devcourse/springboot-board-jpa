package com.example.board.domain.post.entity;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class PostTest {

    @DisplayName("공백, 빈칸인 제목은 Post 생성에 실패한다.")
    @ParameterizedTest
    @ValueSource(strings = {" ", ""})
    void create_post_title_fail(String title) {
        // given
        final String content = "게시글 테스트 내용";

        // when & then
        assertThatThrownBy(() -> Post.builder()
                .title(title)
                .content(content)
                .build())
                .isExactlyInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("공백, 빈칸인 내용은 Post 생성에 실패한다.")
    @ParameterizedTest
    @ValueSource(strings = {" ", ""})
    void create_post_content_fail(String content) {
        // given
        final String title = "게시글 테스트 제목";

        // when & then
        assertThatThrownBy(() -> Post.builder()
                .title(title)
                .content(content)
                .build())
                .isExactlyInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("Post 생성에 성공한다.")
    @Test
    void create_post_success() {
        // given
        final String title = "테스트용제목";
        final String content = "테스트용내용";

        // when
        Post post = Post.builder()
                .title(title)
                .content(content)
                .build();

        // then
        assertThat(post)
                .hasFieldOrPropertyWithValue("title", title)
                .hasFieldOrPropertyWithValue("content", content);
    }
}