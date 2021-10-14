package com.prgrms.dlfdyd96.board.post.dto;

import com.prgrms.dlfdyd96.board.common.dto.BaseResponseDto;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;

@Getter
public class PostResponse extends BaseResponseDto {

  private final Long id;
  private final String title;
  private final String content;
  private final String userName;

  @Builder
  public PostResponse(String createdBy, LocalDateTime createdAt,
      LocalDateTime updatedAt, Long id, String title, String content, String userName) {
    super(createdBy, createdAt, updatedAt);
    this.id = id;
    this.title = title;
    this.content = content;
    this.userName = userName;
  }
}
