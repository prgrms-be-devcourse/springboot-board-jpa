package com.example.springbootboardjpa.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

class UserTest {

    @Test
    @DisplayName("name은 null이나 공백이면 안된다.")
    public void NameNullTest() {
        assertThatThrownBy(() ->
                new User(null, 28))
                .isInstanceOf(NullPointerException.class)
                .hasMessage("이름은 Null 이거나 공백일 수 없습니다.");

        assertThatThrownBy(() ->
                new User(" ", 28))
                .isInstanceOf(NullPointerException.class)
                .hasMessage("이름은 Null 이거나 공백일 수 없습니다.");
    }

    @Test
    @DisplayName("age 값은 0보다 커야야한다.")
    public void ageZeroTest() {

        assertThatThrownBy(() ->
                new User("영지", 0))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("나이는 0보다 커야합니다.");

        assertThatThrownBy(() ->
                new User("영지", -1))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("나이는 0보다 커야합니다.");

    }

    @Test
    @DisplayName("age는 null이면 안된다.")
    public void ageNullTest() {
        assertThatThrownBy(() ->
                new User("영지", null))
                .isInstanceOf(NullPointerException.class)
                .hasMessage("나이 Null 일 수 없습니다.");
    }

    @Test
    @DisplayName("hobby는 50자를 초과하면 안된다.")
    public void UserNullTest() {

        assertThatThrownBy(() ->
                new User("영지", 28, "012345678901234567890123456789012345678901234567891"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("취미의 유효 글자 수 50을 초과하였습니다.");
    }

}