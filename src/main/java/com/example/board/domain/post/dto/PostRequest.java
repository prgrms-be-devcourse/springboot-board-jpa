package com.example.board.domain.post.dto;

import lombok.Getter;

@Getter
public class PostRequest {

  private String title;
  private String content;
  private Long memberId;
}
