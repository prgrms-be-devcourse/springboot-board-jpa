package com.example.board.domain.post.dto;

import com.example.board.domain.post.Post;

public record PostResponse(Long id, String title, String content, String createdAt,
                           String createdBy) {

  public static PostResponse from(Post post) {
    return new PostResponse(post.getId(), post.getTitle(), post.getContent(),
        post.getCreatedAt().toString(), post.getCreatedBy());
  }
}