package com.sdardew.board.service;

import com.sdardew.board.dto.post.CreatePostDto;
import com.sdardew.board.dto.post.PostDto;
import com.sdardew.board.dto.post.UpdatePostDto;
import com.sdardew.board.domain.user.User;
import com.sdardew.board.repository.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.*;

@Transactional
@SpringBootTest
class PostServiceTest {

  @Autowired
  UserRepository userRepository;

  @Autowired
  PostService postService;

  private static CreatePostDto newPost = new CreatePostDto("title1", "content1", 1L);

  private static User user;

  @BeforeAll
  static void beforeAll() {
    user = new User();
    user.setName("name");
    user.setAge(20);
    user.setHobby("movie");
    user.setCreatedAt(LocalDateTime.now());
  }

  @AfterEach
  void afterEach() {
    postService.deleteAll();
  }

  @Test
  @DisplayName("CreatePostDto를 통해서 새로운 Post를 생성할 수 있다")
  void testCreatePost() {
    userRepository.save(user);
    PostDto created = postService.createPost(newPost);
    postService.getPost(created .getId());
    assertThat(created.getTitle()).isEqualTo("title1");
  }

  @Test
  @DisplayName("Post를 update할 수 있다")
  void testUpdatePost() {
    userRepository.save(user);
    PostDto created = postService.createPost(newPost);
    PostDto oldPost = postService.getPost(created.getId());

    UpdatePostDto updatePost = new UpdatePostDto("new-title", "new=content");
    postService.updatePost(oldPost.getId(), updatePost);

    PostDto post = postService.getPost(oldPost.getId());
    assertThat(post.getTitle()).isEqualTo(updatePost.getTitle());
  }
}