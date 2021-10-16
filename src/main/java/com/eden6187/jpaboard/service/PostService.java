package com.eden6187.jpaboard.service;

import com.eden6187.jpaboard.controller.PostController.AddPostRequestDto;
import com.eden6187.jpaboard.exception.UserNotFoundException;
import com.eden6187.jpaboard.model.Post;
import com.eden6187.jpaboard.model.User;
import com.eden6187.jpaboard.repository.PostRepository;
import com.eden6187.jpaboard.repository.UserRepository;
import com.eden6187.jpaboard.service.converter.PostConverter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PostService {

  private final PostConverter postConverter;
  private final UserRepository userRepository;
  private final PostRepository postRepository;

  @Transactional
  public Long addPost(AddPostRequestDto addPostRequestDto) throws UserNotFoundException {
    // 1. 영속화 할 객체 선정
    Long userId = addPostRequestDto.getUserId();
    User user = userRepository
        .findById(userId)
        .orElseThrow(
            () -> {
              throw new UserNotFoundException();
            }
        );
    Post post = postConverter.convertPost(addPostRequestDto);

    // 2. Entity 사이의 연관관계 맺어주기
    post.setUser(user);

    // 3. 영속화
    Post savedPost = postRepository.save(post);
    return savedPost.getId();
  }

}
