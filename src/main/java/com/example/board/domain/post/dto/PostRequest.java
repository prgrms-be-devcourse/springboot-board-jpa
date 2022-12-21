package com.example.board.domain.post.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class PostRequest {

  private String title;
  private String content;
  private Long memberId;

  public PostRequest(String title, String content, Long memberId){
    this.title = title;
    this.content = content;
    this.memberId = memberId;
  }
}
