package com.example.spring_jpa_post.user.entity;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

class UserTest {

    @DisplayName("회원 생성(취미는 null 허용) 성공")
    @ParameterizedTest
    @CsvSource(
            {"ys,1,","ys,200,TV"}
    )
    void createUser(String name, int age, Hobby hobby) {
        assertDoesNotThrow(() -> User.builder().name(name).age(age).hobby(hobby).build());
    }

    @DisplayName("이름이 20자 이하면 생성 성공")
    @ParameterizedTest
    @ValueSource(
            strings = {"abcdefghijklmnopqrst","is","123","aaaaaaaaa","a"}
    )
    void name(String name) {
        //given
        assertThat(name).hasSizeLessThanOrEqualTo(20);
        //when then
        assertDoesNotThrow(() -> User.builder().name(name).age(27).hobby(Hobby.TV).build());
    }

    @DisplayName("이름이 null, 공백, 길이가 20이 넘는 경우 생성 불가")
    @ParameterizedTest
    @ValueSource(
            strings = {"","abcdefghijklmnopqrstu"}
    )
    void failCreateUserByName(String name) {
        assertThrows(IllegalArgumentException.class, () -> User.builder().name(name).age(17).hobby(Hobby.TV).build());
    }


    @DisplayName("나이가 1보다 작거나 200살보다 많으면 생성 불가")
    @ParameterizedTest
    @ValueSource(
            ints = {0,-1,201}
    )
    void failCreateUserByAge(int age) {
        assertThrows(IllegalArgumentException.class, () -> User.builder().name("ys").age(age).hobby(Hobby.TV).build());
    }
}