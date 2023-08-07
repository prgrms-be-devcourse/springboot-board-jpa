package com.example.board.domain.post.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

import com.example.board.domain.post.Post;
import com.example.board.domain.post.dto.PostCreateRequest;
import com.example.board.domain.post.dto.PostResponse;
import com.example.board.domain.post.dto.PostUpdateRequest;
import com.example.board.domain.post.repository.PostRepository;
import com.example.board.domain.user.User;
import com.example.board.domain.user.dto.UserResponse;
import com.example.board.domain.user.repository.UserRepository;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

@ExtendWith(MockitoExtension.class)
class PostServiceTest {

  @InjectMocks
  private PostService postService;

  @Mock
  private PostRepository postRepository;

  @Mock
  private UserRepository userRepository;

  private User user;
  private Post post;

  @BeforeEach
  void setUp() {
    user = new User("배준일", 20, "배드민턴");
    post = new Post("제목", "내용", user);
  }

  @Test
  @DisplayName("성공 - 게시글 생성")
  void createPost() {

    // given
    Long userId = 1L;
    String title = "title";
    String content = "content";
    PostCreateRequest postCreateRequest = new PostCreateRequest(userId, title,
        content);

    given(userRepository.findById(any())).willReturn(Optional.ofNullable(user));
    given(postRepository.save(any())).willReturn(post);

    // when
    postService.createPost(postCreateRequest);

    // then
    then(postRepository).should().save(any());
  }

  @Test
  @DisplayName("성공 - 게시글 단건 조회")
  void getPost() {

    // given
    given(postRepository.findById(any())).willReturn(Optional.ofNullable(post));

    // when
    PostResponse postResponse = postService.getPost(1L);
    UserResponse userResponse = postResponse.userResponse();

    // then
    assertThat(postResponse.title()).isEqualTo(post.getTitle());
    assertThat(postResponse.content()).isEqualTo(post.getContent());
    assertThat(userResponse.id()).isEqualTo(user.getId());
    assertThat(userResponse.name()).isEqualTo(user.getName());
    assertThat(userResponse.age()).isEqualTo(user.getAge());
    assertThat(userResponse.hobby()).isEqualTo(user.getHobby());
  }

  @Test
  @DisplayName("성공 - 게시글 페이징 조회")
  void getPosts() {

    // given
    int size = 1;
    Pageable pageable = Pageable.ofSize(size);
    PageImpl<Post> posts = new PageImpl<>(List.of(post), pageable, 1);

    given(postRepository.findAll(pageable)).willReturn(posts);

    // when
    Page<PostResponse> postResponsePage = postService.getPosts(pageable);

    // then
    assertThat(postResponsePage.getContent()).hasSize(1);
  }

  @Test
  @DisplayName("성공 - 게시글 수정")
  void updatePost() {

    // given
    String updateTitle = "제목1";
    String updateContent = "내용1";
    PostUpdateRequest postUpdateRequest = new PostUpdateRequest(updateTitle, updateContent);

    given(postRepository.findById(any())).willReturn(Optional.ofNullable(post));

    // when
    postService.updatePost(post.getId(), postUpdateRequest);

    // then
    assertThat(post.getTitle()).isEqualTo(updateTitle);
    assertThat(post.getContent()).isEqualTo(updateContent);
  }
}