package com.study.board.domain.post.domain;

import org.assertj.core.internal.bytebuddy.utility.RandomString;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;

class PostTest {

    @Test
    void 생성_성공_auditing_적용_x() {
        Post post = Post.create("제목", "내용");

        assertThat(post.getId()).isNull();
        assertThat(post.getTitle()).isEqualTo("제목");
        assertThat(post.getContent()).isEqualTo("내용");
        assertThat(post.getCreatedAt()).isNull();
        assertThat(post.getCreatedBy()).isNull();
    }

    @NullAndEmptySource
    @ParameterizedTest
    void 제목이_null_이거나_비어있으면_생성실패(String title) {
        assertThatIllegalArgumentException()
                .isThrownBy(() -> Post.create(title, "내용"));
    }

    @Test
    void 제목의_길이가_255_보다_크면_생성실패() {
        String title = RandomString.make(255 + 1);

        assertThatIllegalArgumentException()
                .isThrownBy(() -> Post.create(title, "내용"));
    }

    @NullAndEmptySource
    @ParameterizedTest
    void 내용이_null_이거나_비어있으면_생성실패(String content) {
        assertThatIllegalArgumentException()
                .isThrownBy(() -> Post.create("제목", content));
    }
}