package com.prgrms.be.app.domain;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.Set;

class UserTest {
    private static final String NAME = "이수영";
    private static final Integer AGE = 25;
    private static final String HOBBY = "취미";

    private Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

    @ParameterizedTest
    @ValueSource(strings = {
            "취미",
            "",
            "   "
    })
    @DisplayName("정상 범위의 이름의 길이, 나이를 통해 유저를 만들 수 있고 취미는 기입되지 않을 수 있다.")
    void successUserCreateTest(String hobby) {
        // given
        User user = new User(NAME, AGE, hobby);
        // when
        Set<ConstraintViolation<User>> violations = validator.validate(user);
        // then
        Assertions.assertThat(violations).isEmpty();
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "이",
            "김수한무 거북이와 두루미 삼천갑자"
    })
    @DisplayName("유저의 이름의 길이가 2보다 작거나 17보다 길게 설정할 수 없다.")
    void shortAndLongLengthUserNameTest(String name) {
        // given
        User user = new User(name, AGE, HOBBY);
        // when
        Set<ConstraintViolation<User>> violations = validator.validate(user);
        // then
        Assertions.assertThat(violations).isNotEmpty();
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "",
            "   "
    })
    @DisplayName("유저의 이름을 비어있거나 공백만으로 설정할 수 없다.")
    void blankUserNameTest(String name) {
        // then
        Assertions.assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> new User(name, AGE, HOBBY));
    }

    @ParameterizedTest
    @ValueSource(ints = {
            0,
            121
    })
    @DisplayName("유저의 나이를 0이하 120을 초과하게 설정할 수 없다.")
    void minAndMaxUserAgeTest(Integer age) {
        // given
        User user = new User(NAME, age, HOBBY);
        // when
        Set<ConstraintViolation<User>> violations = validator.validate(user);
        // then
        Assertions.assertThat(violations).isNotEmpty();
    }
}