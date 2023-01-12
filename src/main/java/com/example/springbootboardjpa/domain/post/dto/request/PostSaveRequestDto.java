package com.example.springbootboardjpa.domain.post.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PostSaveRequestDto {

  private String title;
  private String content;
  private long memberId;
}
