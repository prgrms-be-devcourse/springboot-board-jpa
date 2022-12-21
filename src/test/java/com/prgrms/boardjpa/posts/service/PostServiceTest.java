package com.prgrms.boardjpa.posts.service;

import com.prgrms.boardjpa.posts.dto.PostDto;
import com.prgrms.boardjpa.posts.dto.PostRequest;
import com.prgrms.boardjpa.posts.repository.PostRepository;
import com.prgrms.boardjpa.users.dto.UserDto;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class PostServiceTest {

  @Autowired
  private PostService postService;

  @Autowired
  private PostRepository postRepository;

  Long postId;

  @BeforeEach
  void createPostTest() {
    //given
    //insert into users(age, hobby, name) values(25, '', "gsw" ); test user data
    PostRequest postRequest = PostRequest.builder()
        .title("title")
        .content("content")
        .userDto(
            UserDto.builder()
                .userId(1L)
                .name("gsw")
                .age(25)
                .hobby("")
                .build()
        )
        .build();

    //when
    PostDto post = postService.createPost(postRequest);
    postId = post.getPostId();

    //then
    assertThat(post.getTitle()).isEqualTo(postRequest.getTitle());
    assertThat(post.getContent()).isEqualTo(postRequest.getContent());
    assertThat(post.getUserDto()).usingRecursiveComparison().isEqualTo(postRequest.getUserDto());
  }

  @AfterEach
  void tearDown() {
    postRepository.deleteAll();
  }

  @Test
  void getPostTest() {
    //given
    Long id = postId;

    //when
    PostDto post = postService.getPost(postId);

    //then
    assertThat(post.getPostId()).isEqualTo(id);
  }

  @Test
  void getPostsTest() {
    //given
    PostRequest postRequest = PostRequest.builder()
        .title("title2")
        .content("content2")
        .userDto(
            UserDto.builder()
                .userId(1L)
                .name("gsw")
                .age(25)
                .hobby("")
                .build()
        )
        .build();
    postService.createPost(postRequest);
    PageRequest page = PageRequest.of(0, 10);

    //when
    Page<PostDto> posts = postService.getPosts(page);

    //then
    assertThat(posts.getTotalElements()).isEqualTo(2);
  }

  @Test
  void updatePostTest() {
    //given
    String updateTitle = "updateTitle";
    PostRequest postRequest = PostRequest.builder()
        .title(updateTitle)
        .content("content")
        .userDto(
            UserDto.builder()
                .userId(1L)
                .name("gsw")
                .age(25)
                .hobby("")
                .build()
        )
        .build();

    //when
    PostDto postDto = postService.updatePost(postId, postRequest);

    //then
    assertThat(postDto.getTitle()).isEqualTo(updateTitle);
  }
}