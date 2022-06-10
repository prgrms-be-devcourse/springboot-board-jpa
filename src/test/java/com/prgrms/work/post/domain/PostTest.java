package com.prgrms.work.post.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;

import com.prgrms.work.error.EntityInvalidException;
import com.prgrms.work.user.domain.User;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class PostTest {

    private static final User USER = User.create("김형욱", 27, "산책");
    private static final String TITLE = "제목";
    private static final String MODIFY_TITLE = "제목수정";
    private static final String CONTENT = "내용내용내용내용내용내용";
    private static final String MODIFY_CONTENT = "수정수정수정수정수정";

    @Test
    void 객체_생성() {
        Post post = Post.create("김형욱", "제목", "내용내용내용내용내용내용", USER);

        assertThat(post.getTitle()).isEqualTo(TITLE);
        assertThat(post.getContent()).isEqualTo(CONTENT);
    }

    @Test
    @DisplayName("제목이 2글자가 넘지 않으면 객체 생성이 실패합니다.")
    void 객체_생성_실페_1() {
        assertThatThrownBy(() -> {
            Post.create("김형욱", "", "내용내용내용내용내용", USER);
        }).isInstanceOf(EntityInvalidException.class);
    }

    @Test
    @DisplayName("내용이 10글자가 넘지 않으면 객체 생성이 실패합니다.")
    void 객체_생성_실페_2() {
        assertThatThrownBy(() -> {
            Post.create("김형욱", "제목", "내용", USER);
        }).isInstanceOf(EntityInvalidException.class);
    }

    @Test
    void 객체_수정() {
        Post post = Post.create("김형욱", "제목", "내용내용내용내용내용", USER);
        post.modify(MODIFY_TITLE, MODIFY_CONTENT);

        assertThat(post.getTitle()).isEqualTo(MODIFY_TITLE);
        assertThat(post.getContent()).isEqualTo(MODIFY_CONTENT);
    }

    @Test
    @DisplayName("수정하려는 제목이 2글자가 넘지 않으면 수정이 실패합니다.")
    void 객체_수정_실패_1() {
        Post post = Post.create("김형욱", "제목", "내용내용내용내용내용", USER);

        assertThatThrownBy(() -> {
            post.modify("", MODIFY_CONTENT);
        }).isInstanceOf(EntityInvalidException.class);
    }

    @Test
    @DisplayName("수정하려는 내용이 10글자가 넘지 않으면 수정이 실패합니다.")
    void 객체_수정_실패_2() {
        Post post = Post.create("김형욱", "제목", "내용내용내용내용내용", USER);

        assertThatThrownBy(() -> {
            post.modify("", MODIFY_CONTENT);
        }).isInstanceOf(EntityInvalidException.class);
    }

}