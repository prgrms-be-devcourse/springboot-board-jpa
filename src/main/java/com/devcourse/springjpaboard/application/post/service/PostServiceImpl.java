package com.devcourse.springjpaboard.application.post.service;

import static com.devcourse.springjpaboard.core.exception.ExceptionMessage.NOT_FOUND_POST;
import static com.devcourse.springjpaboard.core.exception.ExceptionMessage.NOT_FOUND_USER;

import com.devcourse.springjpaboard.application.post.controller.dto.CreatePostRequest;
import com.devcourse.springjpaboard.application.post.controller.dto.UpdatePostRequest;
import com.devcourse.springjpaboard.application.post.converter.PostConverter;
import com.devcourse.springjpaboard.application.post.model.Post;
import com.devcourse.springjpaboard.application.post.repository.PostRepository;
import com.devcourse.springjpaboard.application.post.service.dto.PostResponse;
import com.devcourse.springjpaboard.application.user.model.User;
import com.devcourse.springjpaboard.application.user.repository.UserRepository;
import com.devcourse.springjpaboard.core.exception.NotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class PostServiceImpl implements PostSerivce {

  private final PostRepository postRepository;

  private final UserRepository userRepository;

  private final PostConverter postConverter;

  public PostServiceImpl(PostRepository postRepository, UserRepository userRepository,
      PostConverter postConverter) {
    this.postRepository = postRepository;
    this.userRepository = userRepository;
    this.postConverter = postConverter;
  }

  public PostResponse save(CreatePostRequest createPostRequest) {
    User user = userRepository.findById(createPostRequest.userId())
        .orElseThrow(() -> new NotFoundException(NOT_FOUND_USER));
    Post post = postConverter.convertPostRequest(createPostRequest, user);
    postRepository.save(post);
    return postConverter.convertPostResponse(post);
  }

  @Transactional(readOnly = true)
  public PostResponse findOne(Long id) {
    return postRepository.findById(id)
        .map(postConverter::convertPostResponse)
        .orElseThrow(() -> new NotFoundException(NOT_FOUND_POST));
  }

  public Page<PostResponse> findAll(Pageable pageable) {
    return postRepository.findAll(pageable)
        .map(postConverter::convertPostResponse);
  }

  public PostResponse update(Long id, UpdatePostRequest updatePostRequest) {
    return postRepository.findById(id)
        .map(post -> {
          post.setTitle(updatePostRequest.title());
          post.setContent(updatePostRequest.content());
          return post;
        })
        .map(postConverter::convertPostResponse)
        .orElseThrow(() -> new NotFoundException(NOT_FOUND_POST));
  }


}
