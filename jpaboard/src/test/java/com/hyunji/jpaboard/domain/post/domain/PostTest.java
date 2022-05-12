package com.hyunji.jpaboard.domain.post.domain;

import com.hyunji.jpaboard.domain.user.domain.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;

class PostTest {

    @Test
    void post_생성_성공() {
        Post post = Post.create("title01", "content");

        assertThat(post)
                .extracting(Post::getTitle, Post::getContent)
                .isEqualTo(List.of("title01", "content"));
    }

    @Test
    void isWrittenBy_user를_매핑_할_수_있다() {
        Post post = Post.create("title01", "content");
        User user = User.create("user01", 10, "hobby");

        post.isWrittenBy(user);

        assertThat(post.getUser()).isEqualTo(user);
    }

    @ParameterizedTest
    @NullAndEmptySource
    void title이_공백_혹은_null이면_예외를_던진다(String title) {
        assertThatIllegalArgumentException().isThrownBy(() -> Post.create(title, "content"));
    }

    @ParameterizedTest
    @NullAndEmptySource
    void content가_공백_혹은_null이면_예외를_던진다(String content) {
        assertThatIllegalArgumentException().isThrownBy(() -> Post.create("title01", content));
    }

    @Test
    void isWrittenBy의_user가_null이면_예외를_던진다() {
        Post post = Post.create("title01", "content");

        assertThatIllegalArgumentException().isThrownBy(() -> post.isWrittenBy(null));
    }
}