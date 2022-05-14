package org.prgrms.kdt.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.LongStream;
import javax.persistence.EntityNotFoundException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.prgrms.kdt.domain.Post;
import org.prgrms.kdt.domain.User;
import org.prgrms.kdt.dto.PostDto;
import org.prgrms.kdt.dto.PostDto.PostRequest;
import org.prgrms.kdt.dto.PostDto.PostResponse;
import org.prgrms.kdt.mapper.PostMapper;
import org.prgrms.kdt.repository.PostRepository;
import org.prgrms.kdt.repository.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

@ExtendWith(MockitoExtension.class)
class PostServiceTest {

  @InjectMocks
  PostService postService;

  @Mock
  PostRepository postRepository;

  @Mock
  UserRepository userRepository;

  @Mock
  PostMapper mapper;

  @Mock
  User user;

  @Mock
  Post post;

  @Test
  @DisplayName("새로운 게시글을 생성한다.")
  void test_create_post() {
    // given
    PostRequest request = new PostRequest("제목은", "내용은");
    given(userRepository.getById(1L)).willReturn(user);
    given(mapper.of(request, user)).willReturn(post);
    given(post.getId()).willReturn(1L);
    given(postRepository.save(post)).willReturn(post);

    //when
    Long id = postService.createPost(1L, request);

    //then
    then(postRepository).should().save(post);
    assertThat(id).isEqualTo(1L);
  }

  @Test
  @DisplayName("존재하지 않는 사용자가 게시글을 생성하는 경우 예외가 발생한다.")
  void test_throw_create_post_not_exist_user() {
    //given
    PostRequest request = new PostRequest("제목은", "내용은");

    //when then
    assertThatThrownBy(() -> postService.createPost(1L, request))
        .isInstanceOf(NullPointerException.class);
  }

  @Test
  @DisplayName("게시글을 수정한다.")
  void test_update_post() {
    //given
    PostRequest request = new PostRequest("새로운 제목", "바뀐 내용");
    given(postRepository.findById(1L)).willReturn(Optional.of(post));
    given(post.getId()).willReturn(1L);

    //when
    Long updatedPostId = postService.updatePost(1L, request);

    //then
    then(postRepository).should().findById(1L);
    assertThat(updatedPostId).isEqualTo(1L);
  }

  @Test
  @DisplayName("수정하려는 게시글이 없는 경우 에러가 발생한다.")
  void test_throw_update_when_post_not_exist() {
    //given
    PostRequest request = new PostRequest("새로운 제목", "바뀐 내용");
    given(postRepository.findById(1L)).willReturn(Optional.empty());

    //when then
    assertThatThrownBy(() -> postService.updatePost(1L, request))
        .isInstanceOf(EntityNotFoundException.class)
        .hasMessage("ID: 1의 게시글을 찾을 수 없습니다");
  }

  @Test
  @DisplayName("게시물을 페이지로 조회한다.")
  void test_find_pageable_post() {
    //given
    Pageable pageable = PageRequest.of(0, 10);
    List<Post> posts = LongStream.range(0, 30)
        .mapToObj(i -> Post.builder().build())
        .toList();
    given(postRepository.findAll(pageable)).willReturn(new PageImpl<>(posts));

    //when
    Page<PostDto.PostResponse> findPosts = postService.findPosts(pageable);

    //then
    assertThat(findPosts).hasSize(30);
  }

  @Test
  @DisplayName("게시물을 단건 조회한다.")
  void test_find_post_by_id() {
    //given
    PostResponse response = new PostResponse(
        1L,
        "제목",
        "내용",
        "생성자",
        LocalDateTime.now(),
        LocalDateTime.now());
    given(postRepository.findById(1L)).willReturn(Optional.of(post));
    given(mapper.of(post)).willReturn(response);

    //when
    PostResponse postResponse = postService.findPost(1L);

    //then
    then(postRepository).should().findById(1L);
    assertThat(postResponse.id()).isEqualTo(1L);
  }
}