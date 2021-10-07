package com.prgrms.dlfdyd96.board.user.post.service;

import com.prgrms.dlfdyd96.board.user.domain.Post;
import com.prgrms.dlfdyd96.board.user.post.repository.PostRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class PostService {
  private final PostRepository postRepository;

  public PostService(PostRepository postRepository) {
    this.postRepository = postRepository;
  }

}
