package com.spring.board.springboard.user.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class MemberTest {

    @Test
    @DisplayName("이름은 빈 값일 수 없다.")
    void testNoTitle() {
        assertThatThrownBy(() ->
                new Member("user@naver.com", "password1234", "", 24, Hobby.SLEEP))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("나이는 빈 값일 수 없다.")
    void testNoAge() {
        assertThatThrownBy(() ->
                new Member("user@naver.com", "password1234", "아무개", null, Hobby.SLEEP))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("나이는 10살보다 작을 수 없다.")
    void testAgeUnder10() {
        assertThatThrownBy(() ->
                new Member("user@naver.com", "password1234", "아무개", 9, Hobby.SLEEP))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @ParameterizedTest
    @DisplayName("이메일 형식에 맞지 않는 입력은 허용되지 않는다.")
    @ValueSource(strings = {"user", "user@naver", "user*@.com"})
    void testInvalidEmail(String input) {
        assertThatThrownBy(() ->
                new Member(input, "password1234", "아무개", 20, Hobby.SLEEP))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("이메일은 빈 값일 수 없다.")
    void testNoEmail() {
        assertThatThrownBy(() ->
                new Member(null, "password1234", "아무개", 20, Hobby.SLEEP))
                .isInstanceOf(IllegalArgumentException.class);

        assertThatThrownBy(() ->
                new Member("", "password1234", "아무개", 20, Hobby.SLEEP))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("비밀번호의 길이는 5자리보다 짧을 수 없다.")
    void testShortPasswordTest() {
        assertThatThrownBy(() ->
                new Member("user@naver.com", "pwd", "아무개", 20, Hobby.SLEEP))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("비밀번호의 길이는 30자리보다 길 수 없다.")
    void testLongPasswordTest() {
        String longPassword = "passwordpasswordpasswordpassword";
        assertThatThrownBy(() ->
                new Member("user@naver.com", longPassword, "아무개", 20, Hobby.SLEEP))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("새로운 회원 엔티티를 생성할 수 있다.")
    void testCreateEntity() {
        String name = "아무개";
        Integer age = 25;
        String email = "user@naver.com";
        String password = "password1234";

        Member member = new Member(email, password, name, age, Hobby.SLEEP);

        assertThat(member.getEmail())
                .isEqualTo(email);
        assertThat(member.getPassword())
                .isEqualTo(password);
        assertThat(member.getName())
                .isEqualTo(name);
        assertThat(member.getAge())
                .isEqualTo(age);
        assertThat(member.getHobby())
                .isEqualTo(Hobby.SLEEP);
    }
}