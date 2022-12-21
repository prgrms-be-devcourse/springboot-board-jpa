package com.prgrms.boardjpa.posts.dto;

import com.prgrms.boardjpa.users.dto.UserDto;
import lombok.*;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotBlank;

@Getter
@Builder
@Validated
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class PostRequest {

  @NotBlank
  private String title;

  private String content;

  private UserDto userDto;
}