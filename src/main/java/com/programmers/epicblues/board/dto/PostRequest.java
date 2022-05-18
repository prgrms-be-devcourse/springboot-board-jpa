package com.programmers.epicblues.board.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import lombok.Getter;
import org.hibernate.validator.constraints.Length;

@Getter
public class PostRequest {

  @NotNull(message = "title을 반드시 입력하셔야 합니다.")
  @Length(min = 3, max = 200)
  private final String title;
  @NotNull(message = "content를 반드시 입력하셔야 합니다.")
  @Length(min = 3, max = 10000)
  private final String content;

  @Positive
  private Long userId;

  public PostRequest(String title, String content) {
    this.title = title;
    this.content = content;
  }

}
