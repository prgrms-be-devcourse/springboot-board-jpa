package com.prgrms.boardjpa.posts.dto;

import com.prgrms.boardjpa.users.dto.UserDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotBlank;

@Getter
@Builder
@Validated
@NoArgsConstructor
@AllArgsConstructor
public class PostDto {

  @NotBlank
  private Long postId;

  @NotBlank
  private String title;

  private String content;

  private UserDto userDto;
}