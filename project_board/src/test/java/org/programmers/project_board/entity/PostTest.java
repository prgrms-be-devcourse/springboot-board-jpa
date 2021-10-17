package org.programmers.project_board.entity;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class PostTest {

    @Test
    @DisplayName("게시글 제목은 null일 수 없다")
    void cannot_setTitle_forNull() {
        // then
        assertThrows(IllegalArgumentException.class, () -> {
            // given // when
            Post post = createPost(null, "");
        });
    }

    @Test
    @DisplayName("게시글 제목은 50자 이상이 될 수 없다")
    void cannot_setTitle_withMoreThan_50() {
        // then
        assertThrows(IllegalArgumentException.class, () -> {
            // given // when
            Post post = createPost("1234567890_1234567890_1234567890_1234567890_1234567890", "");
        });
    }

    @Test
    @DisplayName("게시글의 제목과 내용을 수정할 수 있다")
    void update() {
        // given
        String title = "title";
        String content = "content";
        Post post = createPost(title, content);

        // when
        String updatedTitle = "updatedTitle";
        String updatedContent = "updatedContent";
        Post updated = post.update(updatedTitle, updatedContent);

        // then
        assertThat(updated.getId()).isEqualTo(post.getId());
        assertThat(updated.getTitle()).isEqualTo(updatedTitle);
        assertThat(updated.getContent()).isEqualTo(updatedContent);
    }

    private Post createPost(String title, String content) {
        Post post = new Post();
        post.setTitle(title);
        post.setContent(content);

        return post;
    }

}