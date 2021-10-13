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
  private final UserRepository userRepository; // TODO: UserRepository가 PostService에 있어도 되는 일인가?

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
    return postRepository.findAll(pageable)
        .map(postConverter::convertPostResponse);
  }

  public PostResponse findOne(Long id) throws NotFoundException {
    return postRepository.findById(id)
        .map(postConverter::convertPostResponse)
        .orElseThrow(() -> new NotFoundException("게시물을 찾을 수 없습니다."));
  }

  public PostResponse update(Long id, UpdatePostRequest updatePostRequest)
      throws NotFoundException {
    Post post = postRepository.findById(id)
        .orElseThrow(() -> new NotFoundException("게시물을 찾을 수 없습니다."));

    // TODO: 게시물 주인인지 확인 작업 필요. (지금은 간단하게 진행) - security 배우면 진행.
    User user = getUser(updatePostRequest.getUserId());
    validatePostOwner(post, user);

    post.update(updatePostRequest);

    return postConverter.convertPostResponse(post);
  }

  public void delete(Long postId) throws NotFoundException {
    // TODO: 게시물 주인인지 확인 작업 필요. (지금은 안함) - security 배우면 진행.
    Post post = postRepository.findById(postId)
        .orElseThrow(() -> new NotFoundException("게시물을 찾을 수 없습니다."));

    postRepository.delete(post);
  }

  public void validatePostOwner(Post post, User user) throws NotFoundException {
    Long postId = post.getId();
    Long userId = user.getId();
    if (!postId.equals(userId)) throw new NotFoundException("게시물을 찾을 수 없습니다.");
  }
}

