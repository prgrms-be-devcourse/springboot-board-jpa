package com.programmers.iyj.springbootboard.domain.user.domain;

import jdk.jshell.spi.ExecutionControlProvider;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.event.annotation.BeforeTestClass;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

class UserTest {

    public static Validator validator;

    // 참고: https://www.baeldung.com/java-bean-validation-not-null-empty-blank
    @BeforeAll
    public static void setupValidatorInstance() {
        validator = Validation.buildDefaultValidatorFactory().getValidator();
    }

    @Test
    @DisplayName("Name should not be null")
    public void whenNameIsNull() {
        // Given
        User user = User.builder()
                .name(null)
                .build();

        // When
        Set<ConstraintViolation<User>> violations = validator.validate(user);

        // Then
        assertThat(violations.size()).isEqualTo(1);
    }

    @Test
    @DisplayName("Name should not be empty")
    public void whenNameIsEmpty() {
        // Given
        User user = User.builder()
                .name("")
                .build();

        // When
        Set<ConstraintViolation<User>> violations = validator.validate(user);

        // Then
        assertThat(violations.size()).isEqualTo(1);
    }

    @Test
    @DisplayName("Age should not be 0")
    public void whenAgeIsZero() {
        // Given
        User user = User.builder()
                .name("john")
                .age(0)
                .build();

        // When
        Set<ConstraintViolation<User>> violations = validator.validate(user);

        // Then
        assertThat(violations.size()).isEqualTo(1);
    }
}