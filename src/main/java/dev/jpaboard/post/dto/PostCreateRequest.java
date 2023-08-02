package dev.jpaboard.post.dto;

import dev.jpaboard.post.domain.Post;

public record PostCreateRequest(String title, String content) {

  public static Post toPost(PostCreateRequest request, User user) {
    return Post.builder()
            .user(user)
            .title(request.title)
            .content(request.content())
            .build();
  }

}
