package com.example.springbootboardjpa.model;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class UserTest {

    private Validator validator;

    @BeforeEach
    public void setUp(){
        validator = Validation.buildDefaultValidatorFactory().getValidator();
    }

    @Test
    @DisplayName("name은 null이면 안된다.")
    public void NameNullTest() {
        var user = new User(null, 28);

        Set<ConstraintViolation<User>> validate = validator.validate(user);
        assertThat(validate.size()).isEqualTo(1);
    }

    @Test
    @DisplayName("age 값은 0보다 커야야한다.")
    public void ageZeroTest() {
        var user1 = new User("영지", 0);
        var user2 = new User("영지", -1);

        Set<ConstraintViolation<User>> validate = validator.validate(user1);
        assertThat(validate.size()).isEqualTo(1);
        assertThat(validate.iterator().next().getMessage()).isEqualTo("나이는 0보다 커야합니다.");

        validate = validator.validate(user2);
        assertThat(validate.size()).isEqualTo(1);
        assertThat(validate.iterator().next().getMessage()).isEqualTo("나이는 0보다 커야합니다.");
    }

    @Test
    @DisplayName("age는 null이면 안된다.")
    public void ageNullTest() {
        var user = new User("영지", null);

        Set<ConstraintViolation<User>> validate = validator.validate(user);
        assertThat(validate.size()).isEqualTo(1);
    }

    @Test
    @DisplayName("hobby는 50자를 초과하면 안된다.")
    public void UserNullTest() {
        var user = new User("영지", 28,"012345678901234567890123456789012345678901234567891");

        Set<ConstraintViolation<User>> validate = validator.validate(user);
        assertThat(validate.size()).isEqualTo(1);
        assertThat(validate.iterator().next().getMessage()).isEqualTo("유효 글자 수를 초과하였습니다.");
    }

}