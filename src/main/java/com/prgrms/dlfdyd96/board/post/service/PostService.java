package com.prgrms.dlfdyd96.board.post.service;

import com.prgrms.dlfdyd96.board.domain.Post;
import com.prgrms.dlfdyd96.board.post.converter.PostConverter;
import com.prgrms.dlfdyd96.board.post.dto.PostDto;
import com.prgrms.dlfdyd96.board.post.repository.PostRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@Transactional
public class PostService {
  private final PostRepository postRepository;

  private final PostConverter postConverter;

  public PostService(
      PostRepository postRepository,
      PostConverter postConverter) {
    this.postRepository = postRepository;
    this.postConverter = postConverter;
  }

  public Long save(PostDto postDto) {
    // 1. dto -> entity 변환
    Post post = postConverter.convertPost(postDto);
    // 2. save() 영속화
    // 3. 결과 반환
    return postRepository.save(post)
        .getId();
  }
}
