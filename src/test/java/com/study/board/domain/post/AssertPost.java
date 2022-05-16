package com.study.board.domain.post;

import com.study.board.domain.post.domain.Post;
import com.study.board.domain.user.domain.User;

import static org.assertj.core.api.Assertions.assertThat;

public class AssertPost {

    public static void assertPostCreation(Post post) {
        assertThat(post.getId()).isNull();
        assertThat(post.getTitle()).isEqualTo("제목");
        assertThat(post.getContent()).isEqualTo("내용");
        assertThat(post.getWrittenDateTime()).isNotNull();
    }

    public static void assertPost(Post post, String title, String content) {
        assertThat(post.getId()).isNotNull();
        assertThat(post.getTitle()).isEqualTo(title);
        assertThat(post.getContent()).isEqualTo(content);
        assertThat(post.getWrittenDateTime()).isNotNull();
    }

    public static void assertWriter(User writer, Long expectedId) {
        assertThat(writer.getId()).isEqualTo(expectedId);
        assertThat(writer.getName()).isEqualTo("득윤");
        assertThat(writer.getHobby()).isEqualTo("체스");
    }

    public static void assertPostWithWriter(Post post, String title, String content, Long expectedWriterId) {
        assertPost(post, title, content);
        assertWriter(post.getWriter(), expectedWriterId);
    }
}
