package com.prgrms.boardapp.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.prgrms.boardapp.common.PostCreateUtil.createPostWithId;
import static com.prgrms.boardapp.common.PostCreateUtil.createPostWithTitle;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertThrows;

class PostTest {

    @Test
    @DisplayName("Post 생성 성공 테스트")
    void testSuccess() {
        Post post = createPostWithId();

        assertAll(
                () -> assertThat(post).isNotNull(),
                () -> assertThat(post.getId()).isNotNull()
        );
    }

    @Test
    @DisplayName("제목 필드의 MAX를 넘어갈 수 없다.")
    void testTitleValidateLength() {
        String maxOver = "*".repeat(Post.TITLE_MAX_LENGTH + 1);

        assertThrows(IllegalArgumentException.class, () -> createPostWithTitle(maxOver));
    }

    @Test
    @DisplayName("제목은 null이나 공백을 허용하지 않는다.")
    void testNotNull() {
        assertAll(
                () -> assertThrows(IllegalArgumentException.class, () -> createPostWithTitle(null)),
                () -> assertThrows(IllegalArgumentException.class, () -> createPostWithTitle("")),
                () -> assertThrows(IllegalArgumentException.class, () -> createPostWithTitle("     "))
        );
    }
}