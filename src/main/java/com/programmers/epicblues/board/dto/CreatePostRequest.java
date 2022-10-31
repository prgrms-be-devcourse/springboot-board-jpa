package com.programmers.epicblues.board.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.hibernate.validator.constraints.Length;

@Getter
@RequiredArgsConstructor
public class CreatePostRequest {

  @NotNull(message = "title을 반드시 입력하셔야 합니다.")
  @Length(min = 3, max = 200, message = "길이가 3 이상 200 이하여야 합니다.")
  private final String title;
  @NotNull(message = "content를 반드시 입력하셔야 합니다.")
  @Length(min = 3, max = 10000, message = "길이가 3 이상 10000 이하여야 합니다.")
  private final String content;

  @Positive
  private final Long userId;

}
