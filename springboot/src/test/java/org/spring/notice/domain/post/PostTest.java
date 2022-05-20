package org.spring.notice.domain.post;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;

import static org.assertj.core.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThatNullPointerException;
import static org.spring.notice.fixture.Fixture.getUser;

class PostTest {

    @ParameterizedTest
    @NullAndEmptySource
    void 제목은_공백일수_없음(String title) {
        assertThatIllegalArgumentException().isThrownBy(
                () -> Post.write(title, "aaaa", getUser())
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
        Post post = Post.write("테스트 제목", "테스트 내용", getUser());

        assertThat(post)
                .usingRecursiveComparison()
                .isEqualTo(Post.write("테스트 제목", "테스트 내용", getUser()));
    }
}