package com.example.board.domain.post.dto;

import com.example.board.domain.post.Post;
import com.example.board.domain.user.dto.UserResponse;
import java.time.LocalDateTime;

public record PostResponse(Long id, String title, String content, String author,
                           LocalDateTime createdAt,
                           String createdBy) {

  public static PostResponse from(Post post) {
    UserResponse userResponse = UserResponse.from(post.getUser());

    return new PostResponse(post.getId(), post.getTitle(), post.getContent(), userResponse.name(),
        post.getCreatedAt(), post.getCreatedBy());
  }
}