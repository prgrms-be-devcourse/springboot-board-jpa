package com.study.board.domain.post.domain;

import com.study.board.domain.user.domain.User;
import org.assertj.core.internal.bytebuddy.utility.RandomString;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;

import static com.study.board.domain.post.AssertPost.assertPostCreation;
import static com.study.board.domain.post.AssertPost.assertWriter;
import static com.study.board.domain.post.domain.Post.POST_TITLE_MAX_LENGTH;
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
}