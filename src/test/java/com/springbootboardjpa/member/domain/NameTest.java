package com.springbootboardjpa.member.domain;

import com.springbootboardjpa.member.exception.InValidMemberException;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

class NameTest {

    @Test
    void 유효하지_않는_이름을_생성하면_예외가_발생한다() {
        // given
        String firstName = "가나다라마바";
        String lastName = "이";

        // when & then
        assertThatThrownBy(() -> new Name(firstName, lastName))
                .isInstanceOf(InValidMemberException.class)
                .hasMessage(String.format("성을 제외하고 이름은 %d자를 초과할 수 없습니다.", Name.MAX_LAST_LENGTH));
    }
}
