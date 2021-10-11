package com.prgrms.dlfdyd96.board.post.service;

import com.prgrms.dlfdyd96.board.domain.Post;
import com.prgrms.dlfdyd96.board.post.converter.PostConverter;
import com.prgrms.dlfdyd96.board.post.dto.PostDto;
import com.prgrms.dlfdyd96.board.post.repository.PostRepository;
import java.util.List;
import javassist.NotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

  public Page<PostDto> findPosts(Pageable pageable) {
    return postRepository.findAll(pageable).map(postConverter::convertPostDto);
  }

  public PostDto findOne(Long id) throws NotFoundException {
    return postRepository.findById(id)
        .map(postConverter::convertPostDto)
        .orElseThrow(() -> new NotFoundException("게시물을 찾을 수 없습니다."));
  }
}
