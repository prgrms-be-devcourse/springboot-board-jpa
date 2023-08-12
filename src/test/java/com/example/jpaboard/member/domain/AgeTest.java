package com.example.jpaboard.member.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

class AgeTest {

    static final int MINUS_AGE = -10;

    @Test
    @DisplayName("나이에 대해서 음수를 입력한 경우 예외를 던진다.")
    void member_MinusAge_throwsException() {
        //when_then
        assertThatThrownBy(() -> new Age(MINUS_AGE))
                .isInstanceOf(IllegalArgumentException.class);
    }

}
