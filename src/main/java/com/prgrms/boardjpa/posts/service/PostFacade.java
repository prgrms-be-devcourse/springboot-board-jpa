package com.prgrms.boardjpa.posts.service;

import com.prgrms.boardjpa.posts.dto.PostDto;
import com.prgrms.boardjpa.posts.dto.PostRequest;
import com.prgrms.boardjpa.users.service.UserService;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class PostFacade {

  private final PostService postService;

  private final UserService userService;

  public PostFacade(PostService postService, UserService userService) {
    this.postService = postService;
    this.userService = userService;
  }

  @Transactional
  public PostDto createPost(PostRequest postRequest) {
    userService.getUser(postRequest.getUserDto().getUserId());
    return postService.createPost(postRequest);
  }

  @Transactional
  public PostDto updatePost(Long postId, PostRequest postRequest) {
    userService.getUser(postRequest.getUserDto().getUserId());
    return postService.createPost(postRequest);
  }

}
