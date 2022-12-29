package com.spring.board.springboard.user.domain;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

class MemberTest {

    private static ValidatorFactory validatorFactory;
    private static Validator validator;

    @BeforeAll
    static void setUp() {
        validatorFactory = Validation.buildDefaultValidatorFactory();
        validator = validatorFactory.getValidator();
    }

    @Test
    @DisplayName("이름은 빈 값일 수 없다.")
    void testNoTitle() {
        // given
        Member newMember = new Member("", 24, Hobby.SLEEP);

        // when
        Set<ConstraintViolation<Member>> violations = validator.validate(newMember);

        // then
        assertThat(1).isEqualTo(violations.size());
        violations.forEach(
                memberConstraintViolation ->
                        assertThat("이름은 빈 값일 수 없습니다.")
                                .isEqualTo(
                                        memberConstraintViolation.getMessage())
        );
    }

    @Test
    @DisplayName("나이는 빈 값일 수 없다.")
    void testNoAge() {
        // given
        Member newMember = new Member("아무개", null, Hobby.SLEEP);

        // when
        Set<ConstraintViolation<Member>> violations = validator.validate(newMember);

        // then
        assertThat(1).isEqualTo(violations.size());
        violations.forEach(
                memberConstraintViolation ->
                        assertThat("나이는 빈 값일 수 없습니다.")
                                .isEqualTo(
                                        memberConstraintViolation.getMessage())
        );
    }

    @Test
    @DisplayName("나이는 10살보다 작을 수 없다.")
    void testAgeUnder10() {
        // given
        Member newMember = new Member("어린이", 9, Hobby.SLEEP);

        // when
        Set<ConstraintViolation<Member>> violations = validator.validate(newMember);

        // then
        assertThat(1).isEqualTo(violations.size());
        violations.forEach(
                memberConstraintViolation ->
                        assertThat("10살 미만은 서비스 이용이 불가능합니다.")
                                .isEqualTo(
                                        memberConstraintViolation.getMessage())
        );
    }
}