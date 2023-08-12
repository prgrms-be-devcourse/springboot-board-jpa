package com.programmers.jpa_board.post.domain;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

class PostDomainTest {
    @Test
    void 게시글의_제목이_빈_값인_경우_예외() {
        //when & then
        assertThatThrownBy(() -> new Post("", "내용"))
                .isInstanceOf(IllegalArgumentException.class);
    }
}
