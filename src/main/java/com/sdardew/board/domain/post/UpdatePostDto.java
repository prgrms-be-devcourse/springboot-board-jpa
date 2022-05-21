package com.sdardew.board.domain.post;

public class UpdatePostDto {
  private String title;
  private String content;

  public UpdatePostDto(String title, String content) {
    this.title = title;
    this.content = content;
  }

  public String getTitle() {
    return title;
  }

  public String getContent() {
    return content;
  }
}
