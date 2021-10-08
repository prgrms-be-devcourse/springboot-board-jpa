package com.prgrms.dlfdyd96.board.user.dto;

import com.prgrms.dlfdyd96.board.post.dto.PostDto;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {
  private String name;
  private int age;
  private String hobby;

  private List<PostDto> postDtos;
}
