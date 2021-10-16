package com.eden6187.jpaboard.service;

import com.eden6187.jpaboard.common.ErrorCode;
import com.eden6187.jpaboard.controller.PostController.AddPostRequestDto;
import com.eden6187.jpaboard.controller.PostController.UpdatePostRequestDto;
import com.eden6187.jpaboard.controller.PostController.UpdatePostResponseDto;
import com.eden6187.jpaboard.exception.AuthorizationException;
import com.eden6187.jpaboard.exception.not_found.PostNotFoundException;
import com.eden6187.jpaboard.exception.not_found.UserNotFoundException;
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
              throw new UserNotFoundException(ErrorCode.USER_NOT_FOUND);
            }
        );
    Post post = postConverter.convertPost(addPostRequestDto);

    // 2. Entity 사이의 연관관계 맺어주기
    post.setUser(user);

    // 3. 영속화
    Post savedPost = postRepository.save(post);
    return savedPost.getId();
  }

  @Transactional
  public UpdatePostResponseDto updatePost(UpdatePostRequestDto updatePostRequestDto, Long postId)
      throws PostNotFoundException, AuthorizationException, UserNotFoundException {
    // 1.영속화
    Post post = postRepository.findById(postId)
        .orElseThrow(
            () -> {
              throw new PostNotFoundException(ErrorCode.POST_NOT_FOUND);
            }
        );
    User user = userRepository.findById(updatePostRequestDto.getUserId())
        .orElseThrow(
            () -> {
              throw new UserNotFoundException(ErrorCode.USER_NOT_FOUND);
            }
        );

    // 2. post를 쓴 user와 request에서 들어온 user의 ID를 비교
    if (!post.isBelongedTo(user)) {
      throw new AuthorizationException(ErrorCode.NO_AUTHORIZATION);
    }

    post.updateContent(updatePostRequestDto.getContent());
    post.updateTitle(updatePostRequestDto.getTitle());

    Post updatedPost = postRepository.save(post);

    return UpdatePostResponseDto
        .builder()
        .title(updatedPost.getTitle())
        .content(updatedPost.getContent())
        .build();
  }
}
