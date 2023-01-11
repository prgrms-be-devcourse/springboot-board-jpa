package com.prgrms.boardjpa.posts.facade;

import com.prgrms.boardjpa.posts.dto.PostDto;
import com.prgrms.boardjpa.posts.dto.PostRequest;
import com.prgrms.boardjpa.posts.service.PostService;
import com.prgrms.boardjpa.users.service.UserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PostFacade {

  private final PostService postService;

  private final UserService userService;

  public PostFacade(PostService postService, UserService userService) {
    this.postService = postService;
    this.userService = userService;
  }

  //user를 조회하고 하는 것은 정석적인 방법 and 검증의 기회
  @Transactional
  public PostDto createPost(PostRequest postRequest) {
    userService.getUser(postRequest.getUserDto().getUserId());
    return postService.createPost(postRequest);
  }

  @Transactional
  public PostDto updatePost(Long postId, PostRequest postRequest) {
    userService.getUser(postRequest.getUserDto().getUserId());
    return postService.updatePost(postId, postRequest);
  }

}
