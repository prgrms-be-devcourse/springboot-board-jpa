package com.prgrms.dlfdyd96.board.user.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PageDto {
  private Long id;
  private String title;
  private String content;
}
