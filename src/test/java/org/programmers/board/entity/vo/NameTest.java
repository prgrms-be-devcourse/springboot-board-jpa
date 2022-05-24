package org.programmers.board.entity.vo;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.programmers.board.exception.TooLongNameException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class NameTest {

    @Test
    @DisplayName("이름 생성 - 성공")
    void testCreateName() {
        Name name = new Name("김지웅");

        assertThat(name).isNotNull();
    }

    @Test
    @DisplayName("이름 생성 - 실패 : 공백")
    void testCreateNameFailByBlank() {
        assertThatThrownBy(() -> new Name(""))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("이름은 공백일 수 없습니다.");
    }

    @Test
    @DisplayName("이름 생성 - 실패 : 글자수 초과")
    void testCreateNameFailByLength() {
        assertThatThrownBy(() -> new Name("thisistoolongname"))
                .isInstanceOf(TooLongNameException.class)
                .hasMessageContaining("이름이 너무 깁니다. 15자 이하로 해주세요.");
    }
}