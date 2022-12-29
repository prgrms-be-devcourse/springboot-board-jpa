package com.example.board.domain.member.entity;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Set;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@TestInstance(Lifecycle.PER_CLASS)
class MemberValidationTest {
  private static final String NAME = "김환";
  private static final String EMAIL = "email123@naver.com";
  private static final String PASSWORD = "password123!";
  private static final int AGE = 25;
  private static final String HOBBY = "게임";

  private final Logger log = LoggerFactory.getLogger(getClass());

  private Validator validator;

  @BeforeAll
  public void setUp() {
    ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
    validator = factory.getValidator();
  }

  @Test
  @DisplayName("사용자의 이름은 null일 수 없습니다")
  void nameNotNull(){
    //given & when
    Member member = new Member(null, EMAIL, PASSWORD, AGE, HOBBY);

    //then
    Set<ConstraintViolation<Member>> violations = validator.validate(member);
    violations.forEach(violation -> log.info("{}", violation));

    int actualViolationSize = violations.size();
    assertThat(actualViolationSize).isEqualTo(1);
  }

  @ParameterizedTest
  @ValueSource(strings = {"한", "열한글자열한글자열한글"})
  @DisplayName("사용자의 이름은 한글이름 2글자 이상 10글자 이하여야 합니다")
  void nameOutOfBoundary(String wrongBoundaryName){
    //given & when
    Member member = new Member(wrongBoundaryName, EMAIL, PASSWORD, AGE, HOBBY);

    //then
    Set<ConstraintViolation<Member>> violations = validator.validate(member);
    violations.forEach(violation -> log.info("{}", violation));

    int actualViolationSize = violations.size();
    assertThat(actualViolationSize).isEqualTo(1);
  }

  @Test
  @DisplayName("사용자의 email은 null일 수 없습니다")
  void emailNotNull(){
    //given & when
    Member member = new Member(NAME, null, PASSWORD, AGE, HOBBY);

    //then
    Set<ConstraintViolation<Member>> violations = validator.validate(member);
    violations.forEach(violation -> log.info("{}", violation));

    int actualViolationSize = violations.size();
    assertThat(actualViolationSize).isEqualTo(1);
  }

  @ParameterizedTest
  @ValueSource(strings = {"abc", "qwe@", "@naver.com", "qwe.@naver.com", "qwe@@naver.com",
      "q@we@naver.com", "qwe@naver..com"})
  @DisplayName("사용자의 email은 알맞은 email의 형식이어야 합니다.")
  void emailPatternNotMatch(String wrongEmailPatternString){
    //given & when
    Member member = new Member(NAME, wrongEmailPatternString, PASSWORD, AGE, HOBBY);

    //then
    Set<ConstraintViolation<Member>> violations = validator.validate(member);
    violations.forEach(violation -> log.info("{}", violation));

    int actualViolationSize = violations.size();
    assertThat(actualViolationSize).isEqualTo(1);
  }

  @Test
  @DisplayName("사용자의 password는 null일 수 없습니다")
  void passwordNotNull(){
    //given & when
    Member member = new Member(NAME, EMAIL, null, AGE, HOBBY);

    //then
    Set<ConstraintViolation<Member>> violations = validator.validate(member);
    violations.forEach(violation -> log.info("{}", violation));

    int actualViolationSize = violations.size();
    assertThat(actualViolationSize).isEqualTo(1);
  }

  @ParameterizedTest
  @ValueSource(strings = {"abcde", "aaaaaaaaaaaaaaaaaaaaa"})
  @DisplayName("사용자의 비밀번호는 6글자 이상 20글자 이하여야 합니다.")
  void passwordOutOfBoundary(String wrongBoundaryPassword){
    //given & when
    Member member = new Member(NAME, EMAIL, wrongBoundaryPassword, AGE, HOBBY);

    //then
    Set<ConstraintViolation<Member>> violations = validator.validate(member);
    violations.forEach(violation -> log.info("{}", violation));

    int actualViolationSize = violations.size();
    assertThat(actualViolationSize).isEqualTo(1);
  }

  @ParameterizedTest
  @ValueSource(ints = {5, 141})
  @DisplayName("사용자의 나이는 6세 이상 140세 이하여야 합니다.")
  void ageOutOfBoundary(int wrongBoundaryAge){
    //given & when
    Member member = new Member(NAME, EMAIL, PASSWORD, wrongBoundaryAge, HOBBY);

    //then
    Set<ConstraintViolation<Member>> violations = validator.validate(member);
    violations.forEach(violation -> log.info("{}", violation));

    int actualViolationSize = violations.size();
    assertThat(actualViolationSize).isEqualTo(1);
  }

  @Test
  @DisplayName("사용자의 취미는 없을 수 있습니다")
  void hobbyCanBeNull(){
    //given & when
    Member member = new Member(NAME, EMAIL, PASSWORD, AGE, null);

    //then
    Set<ConstraintViolation<Member>> violations = validator.validate(member);
    violations.forEach(violation -> log.info("{}", violation));

    int actualViolationSize = violations.size();
    assertThat(actualViolationSize).isZero();
  }

  @ParameterizedTest
  @ValueSource(strings = {"스물한글자취미스물한글자취미스물한글자취미"})
  @DisplayName("사용자의 취미는 20글자 이하여야 합니다")
  void hobbyOutOfBoundary(String wrongBoundaryHobby){
    //given & when
    Member member = new Member(NAME, EMAIL, PASSWORD, AGE, wrongBoundaryHobby);

    //then
    Set<ConstraintViolation<Member>> violations = validator.validate(member);
    violations.forEach(violation -> log.info("{}", violation));

    int actualViolationSize = violations.size();
    assertThat(actualViolationSize).isEqualTo(1);
  }

  @Test
  @DisplayName("사용자가 잘못된 데이터로 사용자정보를 업데이트할 수 없습니다")
  void updateFailure(){
    //given
    Member member = new Member(NAME, EMAIL, PASSWORD, AGE, HOBBY);

    //when
    member.update(null, -1, "스물한글자취미스물한글자취미스물한글자취미");

    //then
    Set<ConstraintViolation<Member>> violations = validator.validate(member);
    violations.forEach(violation -> log.info("{}", violation));

    int actualViolationSize = violations.size();
    assertThat(actualViolationSize).isEqualTo(3);
  }
}
