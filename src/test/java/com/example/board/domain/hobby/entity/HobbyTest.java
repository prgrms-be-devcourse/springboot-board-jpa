package com.example.board.domain.hobby.entity;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class HobbyTest {

    @DisplayName("취미 타입이 null이면 취미 생성에 실패한다.")
    @Test
    void create_hobby_hobby_type_null_fail() {
        // given
        final HobbyType hobbyType = null;

        // when & then
        assertThatThrownBy(() -> Hobby.builder()
                .hobbyType(hobbyType)
                .build())
                .isExactlyInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("취미 생성에 성공한다.")
    @Test
    void create_hobby_success() {
        // given
        final HobbyType hobbyType = HobbyType.GAME;

        // when
        Hobby hobby = Hobby.builder()
                .hobbyType(hobbyType)
                .build();

        // then
        assertThat(hobby).hasFieldOrPropertyWithValue("hobbyType", hobbyType);
    }
}