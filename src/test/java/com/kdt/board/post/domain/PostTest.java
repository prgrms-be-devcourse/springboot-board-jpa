package com.kdt.board.post.domain;

import com.kdt.board.common.exception.CannotEditPostException;
import com.kdt.board.user.domain.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;

public class PostTest {

    @Nested
    @DisplayName("update 메소드는")
    class Describe_update {

        @Nested
        @DisplayName("글 작성자와 글 수정 시도자가 다르면")
        class Context_with_author_updated_different {

            @Test
            @DisplayName("CannotEditPostException이 발생한다.")
            void It_response_CannotEditPostException() {
                //given
                final String title = "test title";
                final String content = "test content";
                final User user = user();
                final User another = another();
                final Post post = post(user);

                //when, then
                assertThatCode(() -> post.update(another, title, content)).isInstanceOf(CannotEditPostException.class);
            }
        }

        @Nested
        @DisplayName("글 작성자와 글 수정 시도자가 같다면")
        class Context_with_author_updated_same {

            @Test
            @DisplayName("글을 수정한다.")
            void It_response_update_post() {
                //given
                final String title = "test title";
                final String content = "test content";
                final User user = user();
                final Post post = post(user);

                //when
                post.update(user, title, content);

                //then
                assertThat(post).extracting("title").isEqualTo(title);
                assertThat(post).extracting("content").isEqualTo(content);
            }
        }
    }

    private User user() {
        return User.builder()
                .id(1L)
                .name("first test")
                .email("first test")
                .build();
    }

    private User another() {
        return User.builder()
                .id(2L)
                .name("second test")
                .email("second test")
                .build();
    }

    private Post post(User user) {
        return Post.builder()
                .id(1L)
                .title("test")
                .content("test")
                .user(user)
                .build();
    }
}
