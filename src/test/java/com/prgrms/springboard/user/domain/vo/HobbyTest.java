package com.prgrms.springboard.user.domain.vo;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

import com.prgrms.springboard.global.error.exception.InvalidValueException;

class HobbyTest {

    @DisplayName("취미 객체를 생성한다.")
    @ParameterizedTest
    @ValueSource(strings = {"가나다라마바사아자차카파타하하", "낚시"})
    void hobby_ValidHobby(String input) {
        // given
        Hobby hobby = new Hobby(input);

        // when
        // then
        assertThat(hobby).isNotNull();
    }

    @DisplayName("취미는 null, 공백, 15자 초과면 안 된다.")
    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {"가나다라마바사아자차카파타하하하"})
    void hobby_WrongHobby(String hobby) {
        assertThatThrownBy(() -> new Hobby(hobby))
            .isInstanceOf(InvalidValueException.class);
    }

}
