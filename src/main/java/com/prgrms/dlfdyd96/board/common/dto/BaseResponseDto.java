package com.prgrms.dlfdyd96.board.common.dto;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public abstract class BaseResponseDto {

  private final String createdBy;
  private final LocalDateTime createdAt;
  private final LocalDateTime updatedAt;
}
