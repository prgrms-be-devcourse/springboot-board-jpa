package com.programmers.epicblues.jpa_board.entity;

import java.time.LocalDateTime;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class User {

  private Long id;
  private String name;
  private int age;
  private String hobby;
  private String createdBy;
  private LocalDateTime createdAt;

  public User(String name, int age, String hobby, String createdBy, LocalDateTime createdAt) {
    this.id = null;
    this.name = name;
    this.age = age;
    this.hobby = hobby;
    this.createdAt = createdAt;
    this.createdBy = createdBy;
  }
}
