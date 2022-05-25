package com.sdardew.board.dto.post;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.sdardew.board.dto.user.UserDto;

import java.time.LocalDateTime;

public class DetailedPostDto {

  private Long id;
  private String title;
  private String content;
  private LocalDateTime createAt;
  private UserDto user; // user 테이블과 연관

  public DetailedPostDto(Long id, String title, String content, LocalDateTime createAt, UserDto user) {
    this.id = id;
    this.title = title;
    this.content = content;
    this.createAt = createAt;
    this.user = user;
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

  public LocalDateTime getCreateAt() {
    return createAt;
  }

  public UserDto getUser() {
    return user;
  }
}
