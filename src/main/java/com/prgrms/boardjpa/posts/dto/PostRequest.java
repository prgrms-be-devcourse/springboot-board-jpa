package com.prgrms.boardjpa.posts.dto;

import com.prgrms.boardjpa.users.dto.UserDto;
import lombok.*;

import javax.validation.constraints.NotBlank;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class PostRequest {

  @NotBlank
  private String title;

  private String content;

  private UserDto userDto;
}