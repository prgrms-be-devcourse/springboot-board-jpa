package com.programmers.epicblues.board.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import lombok.Getter;
import org.hibernate.validator.constraints.Length;

@Getter
public class UpdatePostRequest {

  @NotNull(message = "title을 반드시 입력하셔야 합니다.")
  @Length(min = 3, max = 200, message = "길이가 3 이상 200 이하여야 합니다.")
  private final String title;
  @NotNull(message = "content를 반드시 입력하셔야 합니다.")
  @Length(min = 3, max = 10000, message = "길이가 3 이상 10000 이하여야 합니다.")
  private final String content;

  @Positive
  private final Long postId;

  @Positive
  private final Long userId;

  public UpdatePostRequest(String title, String content, Long postId, Long userId) {
    this.title = title;
    this.content = content;
    this.postId = postId;
    this.userId = userId;
  }
}
