package com.programmers.epicblues.jpa_board.dto;

public class PostResponse {

  private final String title;
  private final String content;
  private final Long id;

  public PostResponse(Long id, String title, String content) {
    this.title = title;
    this.content = content;
    this.id = id;
  }

  public String getTitle() {
    return title;
  }

  public String getContent() {
    return content;
  }

  public Long getId() {
    return id;
  }

}
