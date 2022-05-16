package com.prgrms.springboard.user.domain.vo;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import com.prgrms.springboard.global.error.exception.InvalidValueException;

class AgeTest {

    @DisplayName("나이 객체를 생성한다.")
    @Test
    void age_ValidAge() {
        // given
        Age age = new Age(10);

        // when
        // then
        assertThat(age).isNotNull();
    }

    @DisplayName("나이는 음수면 안된다.")
    @ParameterizedTest
    @ValueSource(ints = {-1, -10, -5})
    void age_wrongAge(int age) {
        assertThatThrownBy(() -> new Age(age))
            .isInstanceOf(InvalidValueException.class);
    }

}
