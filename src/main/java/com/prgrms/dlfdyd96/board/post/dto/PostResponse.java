package com.prgrms.dlfdyd96.board.post.dto;

import com.prgrms.dlfdyd96.board.user.dto.UserResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PostResponse {
  private Long id;
  private String title;
  private String content;
  private String userName;
}
