package com.programmers.springbootboardjpa.domain.post;

import com.programmers.springbootboardjpa.domain.user.User;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;
import static org.junit.jupiter.api.Assertions.*;

class PostTest {
    @Test
    void 게시물_작성(){
        Post post = Post.create("Post1");

        assertThat(post.getId()).isNull();
        assertThat(post.getCreatedAt()).isNull();
        assertThat(post.getTitle()).isNotNull();
    }

    @Test
    void 게시물의_title이_null일경우(){
        assertThatIllegalArgumentException().isThrownBy(()
                -> Post.create(null)
        );
    }

}