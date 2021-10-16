package com.eden6187.jpaboard.service.converter;

import com.eden6187.jpaboard.controller.PostController.AddPostRequestDto;
import com.eden6187.jpaboard.model.Post;
import java.time.LocalDateTime;
import org.springframework.stereotype.Component;

@Component
public class PostConverter {

  public Post convertPost(AddPostRequestDto addPostRequestDto) {
    return Post.builder()
        .content(addPostRequestDto.getTitle())
        .title(addPostRequestDto.getTitle())
        .createdAt(LocalDateTime.now())
        .build();
  }
}
