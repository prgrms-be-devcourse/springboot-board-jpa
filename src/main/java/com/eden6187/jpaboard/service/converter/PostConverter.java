package com.eden6187.jpaboard.service.converter;

import com.eden6187.jpaboard.controller.PostController.AddPostRequestDto;
import com.eden6187.jpaboard.controller.PostController.GetSinglePostResponseDto;
import com.eden6187.jpaboard.controller.PostController.UpdatePostRequestDto;
import com.eden6187.jpaboard.model.Post;
import java.time.LocalDateTime;
import org.springframework.stereotype.Component;

@Component
public class PostConverter {

  public Post convertToPost(AddPostRequestDto addPostRequestDto) {
    return Post.builder()
        .content(addPostRequestDto.getTitle())
        .title(addPostRequestDto.getTitle())
        .createdAt(LocalDateTime.now())
        .build();
  }

  public Post convertToPost(UpdatePostRequestDto updatePostRequestDto) {
    return Post.builder()
        .content(updatePostRequestDto.getContent())
        .title(updatePostRequestDto.getTitle())
        .createdAt(LocalDateTime.now())
        .build();
  }

  public GetSinglePostResponseDto convertToDto(Post post) {
    return GetSinglePostResponseDto.builder()
        .title(post.getTitle())
        .content(post.getContent())
        .userId(post.getUser().getId())
        .userName(post.getUser().getName())
        .createdAt(post.getCreatedAt())
        .createdBy(post.getCreatedBy())
        .build();
  }
}
