package com.prgrms.dlfdyd96.board.post.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreatePostRequest {
  @NotBlank
  private String title;
  @NotNull
  private String content;
  @NotNull
  private Long userId;
}
