package com.ys.board.domain.user.model;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class UserTest {

    @DisplayName("User 생성 실패 테스트 - name 빈 값이면 생성에 실패하고 예외를 던진다.")
    @ParameterizedTest
    @ValueSource(strings = {"", " "})
    void createFailFirstNameIsEmpty(String name) {
        //given
        int age = 28;
        String hobby = "";
        //when & then
        assertThrows(IllegalArgumentException.class, () -> new User(name, age, hobby));
    }

    @DisplayName("User 생성 실패 테스트 - age가 1보다 작으면생성에 실패하고 예외를 던진다.")
    @Test
    void createFailFirstNameIsNull() {
        //given
        String name = "name";
        int age = 0;
        String hobby = "";
        //when & then
        assertThrows(IllegalArgumentException.class, () -> new User(name, age, hobby));
    }

    @DisplayName("User 생성 성공 테스트 - name 이 존재하고 age가 0보다 크면 생성에 성공한다")
    @Test
    void createSuccess() {
        //given
        String name = "name";
        int age = 28;
        String hobby = "";
        //when
        User user = assertDoesNotThrow(() -> new User(name, age, hobby));

        // then
        assertEquals(name, user.getName());
        assertEquals(age, user.getAge());
        assertEquals(hobby, user.getHobby());
    }

}