package org.spring.notice.domain.post;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.spring.notice.domain.user.User;
import org.spring.notice.fixture.Fixture;

import java.util.UUID;

import static org.assertj.core.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThatIllegalStateException;
import static org.assertj.core.api.Assertions.assertThatNullPointerException;
import static org.spring.notice.fixture.Fixture.createUser;

class PostTest {

    @ParameterizedTest
    @NullAndEmptySource
    void 제목은_공백일수_없음(String title) {
        assertThatIllegalStateException().isThrownBy(
                () -> Post.write(title, "aaaa", createUser())
        );
    }

    @Test
    void 작성자는_필수(){
        assertThatNullPointerException().isThrownBy(
                () -> Post.write("테스트 제목", "aaaa", null)
        );
    }

    @Test
    void 작성성공() {
        Post post = Post.write("테스트 제목", "테스트 내용", createUser());

        assertThat(post)
                .usingRecursiveComparison()
                .isEqualTo(Post.write("테스트 제목", "테스트 내용", createUser()));
    }
}