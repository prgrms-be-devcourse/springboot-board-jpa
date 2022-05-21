package com.study.board.domain.post.domain;

import com.study.board.domain.user.domain.User;
import org.assertj.core.internal.bytebuddy.utility.RandomString;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;

import java.util.List;

import static com.study.board.domain.post.AssertPost.assertPostCreation;
import static com.study.board.domain.post.AssertPost.assertWriter;
import static com.study.board.domain.post.domain.Post.POST_TITLE_MAX_LENGTH;
import static com.study.board.fixture.Fixture.createPost;
import static com.study.board.fixture.Fixture.createUser;
import static org.assertj.core.api.Assertions.*;

public class PostTest {

    @Test
    void 생성_성공() {
        User writer = createUser();
        Post post = new Post("제목", "내용", writer);

        assertPostCreation(post);
        assertWriter(post.getWriter(), null);
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
    void 작성자가_null_이면_생성_실패() {
        assertThatNullPointerException()
                .isThrownBy(() -> new Post("제목", "내용", null));
    }

    @Test
    void 수정_성공() {
        Post origPost = createPost();

        Post updatedPost = origPost.update("수정제목", "수정내용");

        assertWriter(updatedPost.getWriter(), origPost.getId());
        assertThat(updatedPost.getWrittenDateTime()).isEqualTo(origPost.getWrittenDateTime());

        assertThat(List.of(updatedPost.getTitle(), origPost.getTitle())).allMatch(title -> title.equals("수정제목"));
        assertThat(List.of(updatedPost.getContent(), origPost.getContent())).allMatch(title -> title.equals("수정내용"));
    }

    @NullAndEmptySource
    @ParameterizedTest
    void 제목이_null_이거나_비어있으면_수정실패(String title) {
        assertThatIllegalArgumentException()
                .isThrownBy(() -> createPost().update(title, "수정내용"));
    }

    @Test
    void 제목의_길이가_제한_보다_크면_수정실패() {
        String title = RandomString.make(POST_TITLE_MAX_LENGTH + 1);

        assertThatIllegalArgumentException()
                .isThrownBy(() -> createPost().update(title, "수정내용"));
    }

    @NullAndEmptySource
    @ParameterizedTest
    void 내용이_null_이거나_비어있으면_수정실패(String content) {
        assertThatIllegalArgumentException()
                .isThrownBy(() -> createPost().update("수정제목", content));
    }
}