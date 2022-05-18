package com.programmers.epicblues.jpa_board.dto;

import com.programmers.epicblues.jpa_board.entity.Post;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;

@Getter
public class PostResponse {

  private final String title;
  private final String content;
  private final Long id;
  private final LocalDateTime createdAt;
  private final String createdBy;
  private final Long authorId;

  @Builder
  public PostResponse(Long id, String title, String content, LocalDateTime createdAt,
      String createdBy, Long authorId) {
    this.title = title;
    this.content = content;
    this.id = id;
    this.createdAt = createdAt;
    this.createdBy = createdBy;
    this.authorId = authorId;
  }

  public static PostResponse from(Post post) {
    return PostResponse.builder()
        .authorId(post.getUser().getId())
        .content(post.getContent())
        .title(post.getTitle())
        .createdAt(post.getCreatedAt())
        .createdBy(post.getCreatedBy())
        .id(post.getId())
        .build();
  }

}
