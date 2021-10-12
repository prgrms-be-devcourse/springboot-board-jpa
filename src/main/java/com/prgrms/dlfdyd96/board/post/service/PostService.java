package com.prgrms.dlfdyd96.board.post.service;

import com.prgrms.dlfdyd96.board.domain.Post;
import com.prgrms.dlfdyd96.board.domain.User;
import com.prgrms.dlfdyd96.board.post.converter.PostConverter;
import com.prgrms.dlfdyd96.board.post.dto.CreatePostRequest;
import com.prgrms.dlfdyd96.board.post.dto.PostResponse;
import com.prgrms.dlfdyd96.board.post.dto.UpdatePostRequest;
import com.prgrms.dlfdyd96.board.post.repository.PostRepository;

import com.prgrms.dlfdyd96.board.user.repository.UserRepository;
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
  private final UserRepository userRepository;

  public PostService(
      PostRepository postRepository,
      PostConverter postConverter,
      UserRepository userRepository) {
    this.postRepository = postRepository;
    this.postConverter = postConverter;
    this.userRepository = userRepository;
  }

  public Long save(CreatePostRequest createPostRequest) throws NotFoundException {
    User user = getUser(createPostRequest.getUserId());
    Post post = postConverter.convertPost(createPostRequest, user);
    return postRepository.save(post).getId();
  }

  private User getUser(Long userId) throws NotFoundException {
    return userRepository.findById(userId)
        .orElseThrow(() -> new NotFoundException("사용자를 찾을 수 없습니다."));
  }

  public Page<PostResponse> findPosts(Pageable pageable) {
    return postRepository.findAll(pageable).map(postConverter::convertPostResponse);
  }

  public PostResponse findOne(Long id) throws NotFoundException {
    return postRepository.findById(id)
        .map(postConverter::convertPostResponse)
        .orElseThrow(() -> new NotFoundException("게시물을 찾을 수 없습니다."));
  }

  public Long update(Long id, UpdatePostRequest updatePostRequest) throws NotFoundException {
    Post post = postRepository.findById(id)
        .orElseThrow(() -> new NotFoundException("게시물을 찾을 수 없습니다."));

    post.update(updatePostRequest);

    return post.getId();
  }

  public void delete(Long id) throws NotFoundException {
    Post post = postRepository.findById(id)
        .orElseThrow(() -> new NotFoundException("게시물을 찾을 수 없습니다."));
    postRepository.delete(post);
  }
}

