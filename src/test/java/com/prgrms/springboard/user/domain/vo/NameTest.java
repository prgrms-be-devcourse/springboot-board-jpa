package com.prgrms.springboard.user.domain.vo;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

import com.prgrms.springboard.global.error.exception.InvalidValueException;

class NameTest {

    @DisplayName("이름 객체를 생성한다.")
    @ParameterizedTest
    @ValueSource(strings = {"가나다라마바사아자차", "가나다라마바사아자"})
    void name_ValidName(String input) {
        // given
        Name name = new Name(input);

        // when
        // then
        assertThat(name).isNotNull();
    }

    @DisplayName("이름은 null, 공백, 10자 초과면 안 된다.")
    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {"가나다라마바사아자차카", "가나다라마바사아자차카파타"})
    void name_WrongName(String name) {
        assertThatThrownBy(() -> new Name(name))
            .isInstanceOf(InvalidValueException.class);
    }

}
