package com.prgrms.dlfdyd96.board.post.converter;

import com.prgrms.dlfdyd96.board.domain.Post;
import com.prgrms.dlfdyd96.board.domain.User;
import com.prgrms.dlfdyd96.board.post.dto.CreatePostRequest;
import com.prgrms.dlfdyd96.board.post.dto.PostResponse;
import org.springframework.stereotype.Component;

@Component
public class PostConverter {

  // dto -> entity
  public Post convertPost(CreatePostRequest createPostRequest, User user) {
    return Post.builder()
        .title(createPostRequest.getTitle())
        .content(createPostRequest.getContent())
        .user(user)
        .build();
  }

  // entiy -> dto
  public PostResponse convertPostResponse(Post post) {
    return PostResponse.builder()
        .id(post.getId())
        .title(post.getTitle())
        .content(post.getContent())
        .userName(post.getUser().getName())
        .createdAt(post.getCreatedAt())
        .createdBy(post.getCreatedBy())
        .updatedAt(post.getUpdatedAt())
        .build();
  }
}