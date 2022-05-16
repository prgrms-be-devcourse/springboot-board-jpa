package com.prgrms.springboard.post.domain.vo;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

import com.prgrms.springboard.global.error.exception.InvalidValueException;

class TitleTest {

    @DisplayName("제목 객체를 생성한다.")
    @ParameterizedTest
    @ValueSource(strings = {"가나다라마바사아자차카파타하하하가나다라마바사아자차카파타하", "2022 05 16 TIL"})
    void title_ValidTitle(String input) {
        // given
        Title title = new Title(input);

        // when
        // then
        assertThat(title).isNotNull();
    }

    @DisplayName("제목은 null, 공백, 30자 초과면 안 된다.")
    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {"가나다라마바사아자차카파타하하하가나다라마바사아자차카파타하하"})
    void title_WrongTitle(String title) {
        assertThatThrownBy(() -> new Title(title))
            .isInstanceOf(InvalidValueException.class);
    }

}
