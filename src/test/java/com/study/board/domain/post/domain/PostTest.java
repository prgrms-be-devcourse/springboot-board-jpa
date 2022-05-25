package com.study.board.domain.post.domain;

import org.assertj.core.internal.bytebuddy.utility.RandomString;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;

import static com.study.board.domain.post.domain.Post.POST_TITLE_MAX_LENGTH;
import static com.study.board.fixture.Fixture.createPost;
import static com.study.board.fixture.Fixture.createUser;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;

class PostTest {

    @Test
    void 생성_성공() {
        String title = "제목";
        String content = "내용";

        Post post = new Post(title, content, createUser());

        assertThat(post.getTitle()).isEqualTo(title);
        assertThat(post.getContent()).isEqualTo(content);
    }

    @NullAndEmptySource
    @ParameterizedTest
    void 제목이_null_이거나_비어있으면_생성실패(String title) {
        assertThatIllegalArgumentException()
                .isThrownBy(() -> new Post(title, "내용", createUser()));
    }

    @Test
    void 제목의_길이가_제한_보다_크면_생성실패() {
        String title = RandomString.make(POST_TITLE_MAX_LENGTH + 1);

        assertThatIllegalArgumentException()
                .isThrownBy(() -> new Post(title, "내용", createUser()));
    }

    @NullAndEmptySource
    @ParameterizedTest
    void 내용이_null_이거나_비어있으면_생성실패(String content) {
        assertThatIllegalArgumentException()
                .isThrownBy(() -> new Post("제목", content, createUser()));
    }

    @Test
    void 수정_성공() {
        Post post = createPost();

        Post editedPost = post.edit("수정제목", "수정내용");

        assertThat(editedPost).isEqualTo(post);
        assertThat(post.getTitle()).isEqualTo("수정제목");
        assertThat(post.getContent()).isEqualTo("수정내용");
    }

    @NullAndEmptySource
    @ParameterizedTest
    void 제목이_null_이거나_비어있으면_수정실패(String title) {
        assertThatIllegalArgumentException()
                .isThrownBy(() -> createPost().edit(title, "수정내용"));
    }

    @Test
    void 제목의_길이가_제한_보다_크면_수정실패() {
        String title = RandomString.make(POST_TITLE_MAX_LENGTH + 1);

        assertThatIllegalArgumentException()
                .isThrownBy(() -> createPost().edit(title, "수정내용"));
    }

    @NullAndEmptySource
    @ParameterizedTest
    void 내용이_null_이거나_비어있으면_수정실패(String content) {
        assertThatIllegalArgumentException()
                .isThrownBy(() -> createPost().edit("수정제목", content));
    }
}