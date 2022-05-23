package com.programmers.epicblues.board.dto;

import static org.assertj.core.api.Assertions.assertThat;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class PostRequestTest {

  static final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

  @Test
  @DisplayName("등록된 조건을 어길 경우 조건에 관한 메시지가 담긴 객체들을 반환해야 한다.")
  void validationTest() {

    // Given
    PostRequest postRequest = new PostRequest("y", null);

    // When
    var violationResults = validator.validate(postRequest);

    // Then
    assertThat(violationResults).isNotEmpty();
    var messages = violationResults.stream().map(ConstraintViolation::getMessage);
    assertThat(messages).containsExactlyInAnyOrder("길이가 3 이상 200 이하여야 합니다.",
        "content를 반드시 입력하셔야 합니다.");

  }
}
