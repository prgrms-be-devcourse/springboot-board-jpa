package com.sdardew.board.domain.post;

import java.time.LocalDateTime;

public class PostDto {

  private Long id;
  private String title;
  private String content;
  private LocalDateTime createAt;
  private Long userId; // user 테이블과 연관

  public PostDto(Long id, String title, String content, LocalDateTime createAt, Long userId) {
    this.id = id;
    this.title = title;
    this.content = content;
    this.createAt = createAt;
    this.userId = userId;
  }

  public Long getId() {
    return id;
  }

  public String getTitle() {
    return title;
  }

  public String getContent() {
    return content;
  }
}
