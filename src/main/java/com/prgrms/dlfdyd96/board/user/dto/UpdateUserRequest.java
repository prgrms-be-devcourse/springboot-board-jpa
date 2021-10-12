package com.prgrms.dlfdyd96.board.user.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UpdateUserRequest {
  private String name;
  private int age;
  private String hobby; // TODO: 1급 객체
}
