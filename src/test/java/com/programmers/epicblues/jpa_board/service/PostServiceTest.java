package com.programmers.epicblues.jpa_board.service;

import static com.programmers.epicblues.jpa_board.EntityFixture.getFirstPost;
import static com.programmers.epicblues.jpa_board.EntityFixture.getPostList;
import static com.programmers.epicblues.jpa_board.EntityFixture.getUser;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatNoException;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.programmers.epicblues.jpa_board.EntityFixture;
import com.programmers.epicblues.jpa_board.dto.PostResponse;
import com.programmers.epicblues.jpa_board.entity.Post;
import com.programmers.epicblues.jpa_board.entity.User;
import com.programmers.epicblues.jpa_board.repository.JpaPostRepository;
import com.programmers.epicblues.jpa_board.repository.JpaUserRepository;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Transactional;

@ExtendWith(MockitoExtension.class)
class PostServiceTest {

  @Mock
  private JpaPostRepository postRepository;

  @Mock
  private JpaUserRepository userRepository;

  @InjectMocks
  private PostService postService;

  @Test
  @DisplayName("2개를 보관하는 두 번째 페이지를 반환해야 한다.")
  void find_all_post_with_page() {

    // Given
    List<Post> posts = getPostList();

    // When
    when(postRepository.findAll(PageRequest.of(1, 2))).thenReturn(
        new PageImpl<>(List.of(posts.get(2), posts.get(3))));
    List<Post> queriedPosts = postService.getPosts(PageRequest.of(1, 2));

    // Then
    assertThat(queriedPosts).contains(posts.get(2), posts.get(3));
  }

  @Test
  @DisplayName("findAll()은 repository에게 생성날짜 기준으로 정렬된 post 목록 요청을 위임해야 한다.")
  void find_all_posts_without_page_option() {

    // Given
    List<Post> posts = getPostList();
    // When
    postService.getPosts();

    // Then
    verify(postRepository, times(1)).findAll(Sort.by("createdAt").descending());
  }

  @Test
  @Transactional
  @DisplayName("사용자 id와 title, content로 새로운 post를 만들고 postRepository에 성공적으로 위임할 수 있어야 한다.")
  void test_create_post() {

    // given
    User user = getUser();
    Post post = getFirstPost();
    var postArgumentCaptor = ArgumentCaptor.forClass(Post.class);

    // when
    when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));
    postService.createPost(user.getId(), post.getTitle(), post.getContent());

    // then
    verify(postRepository, times(1)).save(postArgumentCaptor.capture());
    Post capturedPost = postArgumentCaptor.getValue();
    assertThat(capturedPost.getUser().getId()).isEqualTo(user.getId());
    assertThat(capturedPost.getContent()).isEqualTo(post.getContent());

  }

  @Test
  @Transactional
  @DisplayName("Post를 만들 때 주어진 사용자ID가 유효하지 않을 경우에만 RuntimeException을 발생시켜야 한다.")
  void test_create_post_with_invalid_user_id() {

    // given
    Long validUserId = 4L;
    Long invalidUserId = 5L;

    // when
    when(userRepository.findById(validUserId)).thenReturn(Optional.of(getUser()));

    // then
    assertThatCode(() -> postService.createPost(invalidUserId, "무효", "무효"))
        .isInstanceOf(RuntimeException.class);
    assertThatNoException().isThrownBy(() -> postService.createPost(validUserId, "유효", "유효"));
  }

  @Test
  @DisplayName("Post 수정 요청이 성공할 경우 수정된 결과물을 반환해야 한다.")
  void should_query_postById_before_update_post() {

    // Given
    long postId = 1L;
    String updatedTitle = "updated title";
    String updatedContent = "updated content";

    // When
    when(postRepository.findById(postId)).thenReturn(Optional.of(EntityFixture.getFirstPost()));
    PostResponse upsertResult = postService.updatePost(postId, updatedTitle, updatedContent);

    // Then
    verify(postRepository, times(1)).findById(postId);
    assertThat(upsertResult.getTitle()).isEqualTo(updatedTitle);
    assertThat(upsertResult.getContent()).isEqualTo(updatedContent);
    assertThat(upsertResult.getId()).isEqualTo(postId);

  }

  @Test
  @DisplayName("getPostById를 호출하면 PostId를 사용해서 repository의 findById를 호출해야 한다.")
  void getPostById_delegates_repository_findById() {

    // Given
    var postId = 1L;
    var queriedPost = EntityFixture.getFirstPost();

    // When
    when(postRepository.findById(postId)).thenReturn(Optional.of(queriedPost));
    var postResponse = postService.getPostById(postId);

    // Then
    verify(postRepository, times(1)).findById(postId);
    assertThat(postResponse.getContent()).isEqualTo(queriedPost.getContent());
    assertThat(postResponse.getTitle()).isEqualTo(queriedPost.getTitle());

  }

  @Test
  @DisplayName("유효하지 않은 id로 getPostById를 호출할 경우 RuntimeException을 던진다.")
  void getPostById_throws_RuntimeException_when_id_is_invalid() {

    var unregisteredPostId = 1L;

    assertThatThrownBy(() -> postService.getPostById(unregisteredPostId))
        .isInstanceOf(RuntimeException.class);

  }

}
