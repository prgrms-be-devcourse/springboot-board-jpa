package com.prgrms.hyuk.domain.user;

import static com.prgrms.hyuk.exception.ExceptionMessage.AGE_OUT_OF_RANGE_EXP_MSG;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.prgrms.hyuk.exception.AgeOutOfRangeException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class AgeTest {

    @ParameterizedTest
    @ValueSource(ints = {1, 26, 100})
    @DisplayName("age 생성 성공")
    void testCreateAgeSuccess(int age) {
        //given
        //when
        var newAge = new Age(age);

        //then
        assertThat(newAge).isNotNull();
    }

    @ParameterizedTest
    @ValueSource(ints = {0, 101})
    @DisplayName("age 생성 실패")
    void testCreateAgeFailBecauseOutOfRange(int age) {
        //given
        //when
        //then
        assertThatThrownBy(() -> new Age(age))
            .isInstanceOf(AgeOutOfRangeException.class)
            .hasMessageContaining(AGE_OUT_OF_RANGE_EXP_MSG.getMessage());
    }


}