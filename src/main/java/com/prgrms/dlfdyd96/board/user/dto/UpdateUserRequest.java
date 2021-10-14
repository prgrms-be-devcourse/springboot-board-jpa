package com.prgrms.dlfdyd96.board.user.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UpdateUserRequest {
  @NotBlank
  private String name;
  @NotNull
  private int age;
  @NotBlank
  private String hobby; // TODO: 1급 객체
}
