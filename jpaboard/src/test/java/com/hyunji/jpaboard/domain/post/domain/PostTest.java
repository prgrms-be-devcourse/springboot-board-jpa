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
        User user = new User("user01", 20, "hobby");
        Post post = new Post("title01", "content", user);

        assertThat(post)
                .extracting(Post::getTitle, Post::getContent)
                .isEqualTo(List.of("title01", "content"));
    }

    @ParameterizedTest
    @NullAndEmptySource
    void title이_공백_혹은_null이면_예외를_던진다(String title) {
        assertThatIllegalArgumentException().isThrownBy(() -> new Post(title, "content", new User("user01", 20, "hobby")));
    }

    @ParameterizedTest
    @NullAndEmptySource
    void content가_공백_혹은_null이면_예외를_던진다(String content) {
        assertThatIllegalArgumentException().isThrownBy(() -> new Post("title01", content, new User("user01", 20, "hobby")));
    }
}