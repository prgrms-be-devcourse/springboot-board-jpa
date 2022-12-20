package com.sdardew.board.service;

import com.sdardew.board.dto.post.CreatePostDto;
import com.sdardew.board.dto.post.DetailedPostDto;
import com.sdardew.board.dto.post.PostDto;
import com.sdardew.board.dto.post.UpdatePostDto;
import com.sdardew.board.domain.user.User;
import com.sdardew.board.repository.UserRepository;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

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
    user = new User("name", 20, "movie", LocalDateTime.now());
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
    DetailedPostDto oldPost = postService.getPost(created.getId());

    UpdatePostDto updatePost = new UpdatePostDto("new-title", "new-content");
    postService.updatePost(oldPost.getId(), updatePost);

    DetailedPostDto post = postService.getPost(oldPost.getId());

    assertAll(
      () -> assertThat(post.getTitle()).isEqualTo(updatePost.getTitle()),
      () -> assertThat(post.getContent()).isEqualTo(updatePost.getContent())
    );
  }
}