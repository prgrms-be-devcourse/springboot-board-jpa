package com.sdardew.board.service;

import com.sdardew.board.dto.post.CreatePostDto;
import com.sdardew.board.domain.post.Post;
import com.sdardew.board.repository.UserRepository;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class DtoConverter {

  private final UserRepository userRepository;

  public DtoConverter(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  private Post toPost(CreatePostDto createPostDto) {
    Post newPost = new Post();
    newPost.setTitle(createPostDto.getTitle());
    newPost.setContent(createPostDto.getContent());
    newPost.setCreateAt(LocalDateTime.now());
    newPost.setUser(userRepository.getById(createPostDto.getUserId()));
    return newPost;
  }

}
