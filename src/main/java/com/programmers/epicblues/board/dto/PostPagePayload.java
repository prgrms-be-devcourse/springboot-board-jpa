package com.programmers.epicblues.board.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import lombok.Getter;

@Getter
public class PostPagePayload {

  @NotNull(message = "page를 입력해야 합니다.")
  @PositiveOrZero(message = "page는 0 이상이어야 합니다.")
  private final Integer page;

  @NotNull(message = "size를 입력해야 합니다.")
  @Positive(message = "size는 1 이상이어야 합니다")
  private final Integer size;

  public PostPagePayload(Integer page, Integer size) {
    this.page = page;
    this.size = size;
  }

}
