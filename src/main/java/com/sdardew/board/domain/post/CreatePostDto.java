package com.sdardew.board.domain.post;

public class CreatePostDto {
  private String title;
  private String content;
  private Long userId;

  public CreatePostDto() {
  }

  public CreatePostDto(String title, String content, Long userId) {
    this.title = title;
    this.content = content;
    this.userId = userId;
  }

  public String getTitle() {
    return title;
  }

  public String getContent() {
    return content;
  }

  public Long getUserId() {
    return userId;
  }
}
