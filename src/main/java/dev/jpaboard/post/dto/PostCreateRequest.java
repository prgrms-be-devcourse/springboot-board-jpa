package dev.jpaboard.post.dto;

import dev.jpaboard.post.domain.Post;

public record PostCreateRequest(String title, String content) {

  public static Post toPost(PostCreateRequest request) {
    return Post.builder()
            .title(request.title)
            .content(request.content())
            .build();
  }

}
