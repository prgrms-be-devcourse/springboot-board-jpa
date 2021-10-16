package com.eden6187.jpaboard.service;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import com.eden6187.jpaboard.controller.PostController.AddPostRequestDto;
import com.eden6187.jpaboard.exception.UserNotFoundException;
import com.eden6187.jpaboard.model.Post;
import com.eden6187.jpaboard.model.User;
import com.eden6187.jpaboard.repository.PostRepository;
import com.eden6187.jpaboard.repository.UserRepository;
import com.eden6187.jpaboard.service.converter.PostConverter;
import com.eden6187.jpaboard.test_data.PostMockData;
import com.eden6187.jpaboard.test_data.UserMockData;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@SpringBootTest(classes = PostService.class)
class PostServiceTest {

  @Autowired
  PostService postService;
  @MockBean
  UserRepository userRepository;
  @MockBean
  PostRepository postRepository;
  @MockBean
  PostConverter postConverter;

  @Test
  @DisplayName("addPostRequestDto를 통해서 새로운 게시물을 등록할 수 있다.")
  void addPostSuccessTest() {
    //given
    AddPostRequestDto addPostRequestDto = AddPostRequestDto.builder()
        .content(PostMockData.TEST_CONTENT)
        .title(PostMockData.TEST_CONTENT)
        .userId(UserMockData.TEST_ID)
        .build();

    Post post = Post.builder()
        .content(PostMockData.TEST_CONTENT)
        .title(PostMockData.TEST_CONTENT)
        .build();

    User user = User.builder()
        .id(UserMockData.TEST_ID)
        .build();

    when(postConverter.convertPost(addPostRequestDto))
        .thenReturn(post);
    when(userRepository.findById(UserMockData.TEST_ID))
        .thenReturn(Optional.of(user));
    when(postRepository.save(post))
        .thenReturn(post);

    //when
    Long postId = postService.addPost(addPostRequestDto);

    //then
    assertThat(post.getUser(), is(user));
    assertThat(user.getPosts(), hasItem(post));
    assertThat(postId, is(post.getId()));
  }

  @Test
  @DisplayName("DTO 안에 들어있는 userId가 존재하지 않는 UserId인 경우 UserNotFoundException 을 던진다.")
  void addPostFailTest() {
    //given
    AddPostRequestDto addPostRequestDto = AddPostRequestDto.builder()
        .content(PostMockData.TEST_CONTENT)
        .title(PostMockData.TEST_CONTENT)
        .userId(UserMockData.TEST_ID)
        .build();

    when(userRepository.findById(UserMockData.TEST_ID))
        .thenReturn(Optional.empty());

    assertThrows(UserNotFoundException.class, () -> {
      postService.addPost(addPostRequestDto);
    });
  }
}