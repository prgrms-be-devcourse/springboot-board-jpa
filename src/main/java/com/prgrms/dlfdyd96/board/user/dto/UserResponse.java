package com.prgrms.dlfdyd96.board.user.dto;

import com.prgrms.dlfdyd96.board.post.dto.PostResponse;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserResponse {

  private Long id; // null
  private String name;
  private int age;
  private String hobby; // TODO: 1급 객체
  private List<PostResponse> postResponses;
}
