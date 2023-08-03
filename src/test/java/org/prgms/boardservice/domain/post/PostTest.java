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

    private final Long userId = 1L;
    private final String titleMoreThenTwentyLength = "TitleMoreThenTwentyLength";
    private final String contentMoreThanFiveHundredLength = "sit amet cursus sit amet dictum sit amet justo donec enim diam vulputate ut pharetra sit amet aliquam id diam maecenas ultricies mi eget mauris pharetra et ultrices neque ornare aenean euismod elementum nisi quis eleifend quam adipiscing vitae proin sagittis nisl rhoncus mattis rhoncus urna neque viverra justo nec ultrices dui sapien eget mi proin sed libero enim sed faucibus turpis in eu mi bibendum neque egestas congue quisque egestas diam in arcu cursus euismod quis viverra nibh cras pulvinar mattis nunc sed blandit libero volutpat sed cras ornare arcu dui vivamus arcu felis bibendum ut tristique et egestas";

    @ParameterizedTest(name = "test with value [{0}]")
    @NullAndEmptySource
    @ValueSource(strings = {"    ", titleMoreThenTwentyLength})
    @DisplayName("생성자에서 제목이 유효하지 않은 경우 에러가 발생한다.")
    void fail_Post_Constructor_With_Invalid_Title(String title) {
        String content = "content";

        assertThatThrownBy(() -> new Post(title, content, userId))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(INVALID_POST_TITLE.getMessage());
    }

    @ParameterizedTest(name = "test with value [{0}]")
    @NullAndEmptySource
    @ValueSource(strings = {"    ", contentMoreThanFiveHundredLength})
    @DisplayName("생성자에서 내용이 유효하지 않은 경우 에러가 발생한다.")
    void fail_Post_Constructor_With_Invalid_Content(String content) {
        String title = "title";

        assertThatThrownBy(() -> new Post(title, content, userId))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(INVALID_POST_CONTENT.getMessage());
    }

    @Test
    @DisplayName("게시글이 성공적으로 생성된다.")
    void success_New_Post() {
        Post post = new Post("title", "content", userId);

        assertThat(post).isNotNull();
    }

    @ParameterizedTest(name = "test with value [{0}]")
    @NullAndEmptySource
    @ValueSource(strings = {"    ", titleMoreThenTwentyLength})
    @DisplayName("게시글을 유효하지 않은 제목으로 변경할 수 없다.")
    void fail_Change_Post_With_Invalid_Title(String title) {
        Post post = new Post("title", "content", userId);

        assertThatThrownBy(() -> post.changeTitle(title))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(INVALID_POST_TITLE.getMessage());
    }

    @ParameterizedTest(name = "test with value [{0}]")
    @NullAndEmptySource
    @ValueSource(strings = {"    ", contentMoreThanFiveHundredLength})
    @DisplayName("게시글을 유효하지 않은 내용으로 변경할 수 없다.")
    void fail_Change_Post_With_Invalid_Content(String content) {
        Post post = new Post("title", "content", userId);

        assertThatThrownBy(() -> post.changeContent(content))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(INVALID_POST_CONTENT.getMessage());
    }
}
