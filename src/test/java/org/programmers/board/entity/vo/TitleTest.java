package org.programmers.board.entity.vo;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.programmers.board.exception.EmptyTitleException;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

class TitleTest {

    @Test
    @DisplayName("제목 생성 - 성공")
    void testCreateTitle() {

        Title title = new Title("this is title!");

        Assertions.assertThat(title).isNotNull();
    }

    @Test
    @DisplayName("제목 생성 - 실패")
    void testCreateTitleFail() {

        assertThatThrownBy(
                () -> new Title("")).isInstanceOf(EmptyTitleException.class)
                .hasMessageContaining("제목을 입력해주세요.");
    }
}