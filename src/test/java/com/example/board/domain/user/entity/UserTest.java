package com.example.board.domain.user.entity;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class UserTest {

    @DisplayName("0살 이하는 User 생성에 실패한다.")
    @ParameterizedTest
    @ValueSource(ints = {-5, -4, -3, -2, -1, 0})
    void create_user_age_fail(int age) {
        // given
        final String name = "박현서";

        // when & then
        assertThatThrownBy(() -> User.builder()
                .age(age)
                .name(name)
                .build())
                .isExactlyInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("공백, 빈칸인 이름은 User 생성에 실패한다.")
    @ParameterizedTest
    @ValueSource(strings = {" ", ""})
    void create_user_name_fail(String name) {
        // given
        final int age = 11;

        // when & then
        assertThatThrownBy(() -> User.builder()
                .age(age)
                .name(name)
                .build())
                .isExactlyInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("User 생성에 성공한다.")
    @Test
    void create_user_success() {
        // given
        final String name = "박현서";
        final int age = 25;

        // when
        User user = User.builder()
                .name(name)
                .age(age)
                .build();

        // then
        assertThat(user)
                .hasFieldOrPropertyWithValue("name", name)
                .hasFieldOrPropertyWithValue("age", age);
    }

}