package com.programmers.epicblues.board.dto;

import static org.assertj.core.api.Assertions.assertThat;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class PostPagePayloadTest {

  static final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

  @Test
  @DisplayName("등록된 조건을 어길 경우 조건에 관한 메시지가 담긴 객체들을 반환해야 한다.")
  void validationTest() {

    // Given
    PostPagePayload payload = new PostPagePayload(-2, null);

    // When
    var violationResults = validator.validate(payload);

    // Then
    assertThat(violationResults).isNotEmpty();
    var messages = violationResults.stream().map(ConstraintViolation::getMessage);
    assertThat(messages).containsExactlyInAnyOrder("page는 0 이상이어야 합니다.", "size를 입력해야 합니다.");

  }
}
