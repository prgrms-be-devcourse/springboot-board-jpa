package com.poogle.board.model.post;

import com.poogle.board.model.user.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;

public class PostTest {

    @Test
    @DisplayName("Post 생성 테스트")
    void create_valid_post_test() {
        Post post = Post.of("title", "content", "tester");
        post.setUser(User.of("name", 27, "hobby"));
        assertThat(post)
                .hasFieldOrPropertyWithValue("title", "title")
                .hasFieldOrPropertyWithValue("content", "content");
    }

    @Test
    @DisplayName("Post 생성 실패 테스트")
    void fail_create_post_test() {
        assertThatIllegalArgumentException()
                .isThrownBy(() -> Post.of("", "content", "tester"))
                .withMessage("Title must be provided.");
    }

    @Test
    @DisplayName("Post 수정 테스트")
    void update_post_test() {
        Post post = Post.of("title", "content", "tester");
        post.setUser(User.of("name", 27, "hobby"));
        post.update("new title", "new content");
        assertThat(post)
                .hasFieldOrPropertyWithValue("title", "new title")
                .hasFieldOrPropertyWithValue("content", "new content");
    }
}
