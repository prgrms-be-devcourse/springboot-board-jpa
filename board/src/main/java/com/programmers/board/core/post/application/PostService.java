package com.programmers.board.core.post.application;

import com.programmers.board.common.exception.NotFoundException;
import com.programmers.board.core.post.application.dto.PostCreateRequest;
import com.programmers.board.core.post.application.dto.PostResponse;
import com.programmers.board.core.post.application.dto.PostUpdateRequest;
import com.programmers.board.core.post.domain.Post;
import com.programmers.board.core.post.domain.repository.PostRepository;
import com.programmers.board.core.user.domain.User;
import com.programmers.board.core.user.domain.repository.UserRepository;
import javax.persistence.EntityNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PostService {

  private final PostRepository postRepository;
  private final UserRepository userRepository;

  public PostService(PostRepository postRepository, UserRepository userRepository) {
    this.postRepository = postRepository;
    this.userRepository = userRepository;
  }

  @Transactional
  public PostResponse save(PostCreateRequest postCreateRequest) {
    User user = userRepository.findById(postCreateRequest.getUserId())
        .orElseThrow(NotFoundException::new);

    Post post = postRepository.save(postCreateRequest.toEntity(user));
    return PostResponse.of(post);
  }

  @Transactional(readOnly = true)
  public PostResponse findOne(Long id) {
    return postRepository.findById(id)
        .map(PostResponse::of)
        .orElseThrow(() -> new EntityNotFoundException("id와 일치하는 포스팅이 없습니다."));
  }

  @Transactional(readOnly = true)
  public Page<PostResponse> findPosts(Pageable pageable) {
    return postRepository.findAll(pageable)
        .map(PostResponse::of);
  }

  @Transactional
  public PostResponse update(Long id, PostUpdateRequest postDto) {
    Post retrievedPost = postRepository.findById(id)
        .orElseThrow(() -> new EntityNotFoundException("id와 일치하는 포스팅이 없습니다."));

    retrievedPost.updateTitle(postDto.getTitle());
    retrievedPost.updateContent(postDto.getContent());

    return PostResponse.of(retrievedPost);
  }

}
