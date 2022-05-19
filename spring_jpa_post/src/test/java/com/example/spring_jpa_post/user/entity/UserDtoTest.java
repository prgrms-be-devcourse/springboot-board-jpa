package com.example.spring_jpa_post.user.entity;

import com.example.spring_jpa_post.user.dto.UserDto;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
@DisplayName("UserDto validation 테스트")
class UserDtoTest {
    private static ValidatorFactory factory;
    private static Validator validator;

    @BeforeAll
    static void init() {
        factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @DisplayName("나이 1살 이상 200이하면 valid 통과")
    @ParameterizedTest
    @ValueSource(
            ints = {1, 2, 3, 200, 199, 198}
    )
    void createDtoByAge(int age) {
        //given
        UserDto userDto = UserDto.builder().name("yong").age(age).hobby(Hobby.TV).build();
        //when
        Set<ConstraintViolation<UserDto>> violation = validator.validate(userDto);
        //then
        assertThat(violation).isEmpty();
    }

    @DisplayName("나이 1살 이상 200이하가 아니면 실패")
    @ParameterizedTest
    @ValueSource(
            ints = {-1, 0, 201, 202}
    )
    void failCreateDtoByAge(int age) {
        //given
        UserDto userDto = UserDto.builder().name("yong").age(age).hobby(Hobby.TV).build();
        //when
        Set<ConstraintViolation<UserDto>> violation = validator.validate(userDto);
        //then
        assertThat(violation).isNotEmpty();
        violation.forEach(v -> log.info("{}는 {} {}", v.getPropertyPath(), v.getExecutableReturnValue(), v.getMessage()));
    }

    @Test
    @DisplayName("이름이 빈갑,공백이면 실패")
    void failCreateDtoByName() {
        //given
        UserDto userDto = UserDto.builder().age(27).hobby(Hobby.TV).build();
        //when
        Set<ConstraintViolation<UserDto>> violation = validator.validate(userDto);
        //then
        assertThat(violation).isNotEmpty();
        violation.forEach(v -> log.info("{}는 {} {}", v.getPropertyPath(), v.getExecutableReturnValue(), v.getMessage()));
    }
}