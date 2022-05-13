package com.programmers.epicblues.jpa_board.entity;

import java.time.LocalDateTime;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "post")
@Getter
@NoArgsConstructor
public class Post {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;
  private String title;
  private String content;
  private LocalDateTime createdAt;
  private String createdBy;

  @Builder
  public Post(String title, String content, LocalDateTime createdAt, String createdBy) {
    this.title = title;
    this.content = content;
    this.createdAt = createdAt;
    this.createdBy = createdBy;
  }
}
