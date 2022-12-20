package org.programmers.board.entity.vo;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.programmers.board.exception.EmptyContentException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class ContentTest {

    @Test
    @DisplayName("내용 생성 - 성공")
    void testCreateContent() {
        Content content = new Content("this is content!!");

        assertThat(content).isNotNull();
    }

    @Test
    @DisplayName("내용 생성 - 실패")
    void testCreateFail() {
        assertThatThrownBy(() -> new Content(""))
                .isInstanceOf(EmptyContentException.class)
                .hasMessageContaining("내용을 입력해주세요.");
    }
}