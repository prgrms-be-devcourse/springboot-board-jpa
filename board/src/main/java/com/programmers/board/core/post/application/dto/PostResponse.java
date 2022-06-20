package com.programmers.board.core.post.application.dto;

import com.programmers.board.core.post.domain.Post;
import lombok.Builder;
import lombok.Getter;

@Getter
public class PostResponse {

  private Long id;

  private String title;

  private String content;

  private PostResponse() {
  }

  @Builder
  public PostResponse(Long id, String title, String content) {
    this.id = id;
    this.title = title;
    this.content = content;
  }

  public static PostResponse of(Post post) {
    return PostResponse.builder()
        .id(post.getId())
        .title(post.getTitle())
        .content(post.getContent())
        .build();
  }

}
