package com.prgrms.java.domain;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.in;
import static org.junit.jupiter.api.Assertions.assertThrows;

class UserTest {

    @DisplayName("사용자의 나이는 0살 이하일 수 없다.")
    @Test
    void ageUnder0() {
        // when then
        assertThrows(IllegalStateException.class, () -> new User("test", "test@gmail.com", "test", 0, HobbyType.MOVIE));
    }

    @DisplayName("사용자의 비밀번호는 빈 문자열이 될 수 없다.")
    @NullAndEmptySource
    @ParameterizedTest
    void passwordEmpty(String invalid) {
        // when then
        assertThrows(IllegalStateException.class, () -> new User("test", "test@gmail.com", invalid, 26, HobbyType.MOVIE));
    }

    @DisplayName("사용자의 비밀번호는 30글자를 넘을 수 없다.")
    @Test
    void passwordOver30() {
        // when then
        assertThrows(IllegalStateException.class, () -> new User("test", "test@gmail.com", "test111111112341251231123122512345124", 25, HobbyType.MOVIE));
    }

    @DisplayName("사용자의 이름은 빈 문자열이 될 수 없다.")
    @NullAndEmptySource
    @ParameterizedTest
    void nameEmpty(String invalid) {
        // when then
        assertThrows(IllegalStateException.class, () -> new User(invalid, "test@gmail.com", "test", 26, HobbyType.MOVIE));
    }

    @DisplayName("사용자의 이름은 30글자를 초과할 수 없다.")
    @Test
    void nameOver30() {
        // when then
        assertThrows(IllegalStateException.class, () -> new User("Name must be less than or equal to 30 characters.", "test@gmail.com", "test", 26, HobbyType.MOVIE));
    }

    @DisplayName("사용자의 이메일은 빈 문자열이 될 수 없다.")
    @NullAndEmptySource
    @ParameterizedTest
    void emailEmpty(String invalid) {
        // when then
        assertThrows(IllegalStateException.class, () -> new User("test", invalid, "test", 26, HobbyType.MOVIE));
    }

    @DisplayName("사용자의 이메일은 30글자를 초과할 수 없다.")
    @Test
    void emailOver30() {
        // when then
        assertThrows(IllegalStateException.class, () -> new User("test", "test123124512312451231245124@gmail.com", "test", 26, HobbyType.MOVIE));
    }

    @DisplayName("사용자의 이메일은 이메일 형식을 벗어날 수 없다.")
    @Test
    void emailUnsupportedFormat() {
        // when then
        assertThrows(IllegalStateException.class, () -> new User("test", "test@gmail.c.om.12", "test", 26, HobbyType.MOVIE));
    }

}