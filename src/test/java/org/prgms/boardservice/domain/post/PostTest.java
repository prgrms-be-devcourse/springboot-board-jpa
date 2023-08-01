package org.prgms.boardservice.domain.post;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.prgms.boardservice.util.ErrorMessage.INVALID_POST_CONTENT;
import static org.prgms.boardservice.util.ErrorMessage.INVALID_POST_TITLE;

class PostTest {

    @ParameterizedTest(name = "test with value [{0}]")
    @NullAndEmptySource
    @ValueSource(strings = {"    "})
    @DisplayName("제목이 null, 빈 문자열 또는 공백일 경우 에러가 발생한다.")
    void should_ThrowException_When_EmptyTitle(String title) {
        String content = "content";

        assertThatThrownBy(() -> new Post(title, content))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(INVALID_POST_TITLE.getMessage());
    }

    @ParameterizedTest(name = "test with value [{0}]")
    @NullAndEmptySource
    @ValueSource(strings = {"    "})
    @DisplayName("내용이 null, 빈 문자열 또는 공백일 경우 에러가 발생한다.")
    void should_ThrowException_When_EmptyContent(String content) {
        String title = "title";

        assertThatThrownBy(() -> new Post(title, content))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(INVALID_POST_CONTENT.getMessage());
    }

    @Test
    @DisplayName("게시글이 성공적으로 생성된다.")
    void should_Success_When_NewPost() {
        Post post = new Post("title", "content");

        assertThat(post).isNotNull();
    }
}
