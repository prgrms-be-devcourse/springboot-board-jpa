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

class UserTest {

    private static Validator validator;

    @BeforeAll
    static void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @DisplayName("사용자의 이름은 30글자를 초과할 수 없다.")
    @Test
    void nameOver30() {
        // given
        User user = new User("Name must be less than or equal to 30 characters.", "test@gmail.com", "test", 26, HobbyType.MOVIE);
        Set<ConstraintViolation<User>> validate = validator.validate(user);

        // when then
        assertThat(validate.isEmpty()).isFalse();
    }

    @DisplayName("사용자의 이름은 빈 문자열이 될 수 없다.")
    @NullAndEmptySource
    @ParameterizedTest
    void nameEmpty(String invalid) {
        // given
        User user = new User("null", invalid, "test", 26, HobbyType.MOVIE);
        Set<ConstraintViolation<User>> validate = validator.validate(user);

        // when then
        assertThat(validate.isEmpty()).isFalse();

    }

    @DisplayName("사용자의 나이는 1보다 작을 수 없다.")
    @Test
    void ageUnder1() {
        // given
        User user = new User("taek", "test@gmail.com", "test", 0, HobbyType.MOVIE);
        Set<ConstraintViolation<User>> validate = validator.validate(user);

        // when then
        assertThat(validate.isEmpty()).isFalse();
    }
}