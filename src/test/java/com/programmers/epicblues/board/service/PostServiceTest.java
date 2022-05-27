package com.programmers.epicblues.board.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatNoException;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static util.EntityFixture.getFirstPost;
import static util.EntityFixture.getPostList;
import static util.EntityFixture.getUser;

import com.programmers.epicblues.board.dto.CreatePostRequest;
import com.programmers.epicblues.board.dto.PostResponse;
import com.programmers.epicblues.board.dto.UpdatePostRequest;
import com.programmers.epicblues.board.entity.Post;
import com.programmers.epicblues.board.entity.User;
import com.programmers.epicblues.board.repository.JpaPostRepository;
import com.programmers.epicblues.board.repository.JpaUserRepository;
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
import org.springframework.transaction.annotation.Transactional;
import util.EntityFixture;
import util.FieldSetter;

@ExtendWith(MockitoExtension.class)
class PostServiceTest {

  @Mock
  private JpaPostRepository postRepository;

  @Mock
  private JpaUserRepository userRepository;

  @InjectMocks
  private PostService postService;

  @Test
  @DisplayName("2개를 보관하는 두 번째 페이지를 반환하는 요청을 postRepository에 위임해야 한다.")
  void find_all_post_with_page() throws NoSuchFieldException, IllegalAccessException {
    // given
    PageRequest pageRequest = PageRequest.of(0, 2);
    var expectedPostList = getPostList().subList(0, 1);
    var user = getUser();
    FieldSetter.assignId(user, 1L);
    user.addPosts(expectedPostList);

    // when
    when(postRepository.findAll(pageRequest)).thenReturn(new PageImpl<>(expectedPostList));
    postService.getPosts(pageRequest);

    //then
    verify(postRepository, times(1)).findAll(pageRequest);
  }

  @Test
  @Transactional
  @DisplayName("사용자 id와 title, content로 새로운 post를 만들고 postRepository에 성공적으로 위임할 수 있어야 한다.")
  void test_create_post() throws NoSuchFieldException, IllegalAccessException {

    // given
    User user = getUser();
    FieldSetter.assignId(user, 1L);
    Post createdPost = getFirstPost();
    user.addPost(createdPost);
    var postArgumentCaptor = ArgumentCaptor.forClass(Post.class);

    // when
    when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));
    when(postRepository.save(any())).thenReturn(createdPost);
    postService.createPost(new CreatePostRequest(createdPost.getTitle(), createdPost.getContent(), user.getId()));

    // then
    verify(postRepository, times(1)).save(postArgumentCaptor.capture());
    Post capturedPost = postArgumentCaptor.getValue();
    assertThat(capturedPost.getUser().getId()).isEqualTo(user.getId());
    assertThat(capturedPost.getContent()).isEqualTo(createdPost.getContent());

  }

  @Test
  @Transactional
  @DisplayName("Post를 만들 때 주어진 사용자ID가 유효하지 않을 경우에만 RuntimeException을 발생시켜야 한다.")
  void test_create_post_with_invalid_user_id() throws NoSuchFieldException, IllegalAccessException {

    // given
    Long validUserId = 4L;
    Long invalidUserId = 5L;

    var postId = 1L;
    var postToSave = EntityFixture.getFirstPost();
    var user = EntityFixture.getUser();
    postToSave.assignUser(user);
    FieldSetter.assignId(postToSave, postId);

    // when
    when(userRepository.findById(validUserId)).thenReturn(Optional.of(user));
    when(postRepository.save(any())).thenReturn(postToSave);

    // then
    assertThatCode(() -> postService.createPost(new CreatePostRequest("무효", "무효", invalidUserId)))
        .isInstanceOf(RuntimeException.class);
    assertThatNoException().isThrownBy(() -> postService.createPost((new CreatePostRequest("유효", "유효", validUserId))));
  }

  @Test
  @DisplayName("Post 수정 요청이 성공할 경우 수정된 결과물을 반환해야 한다.")
  void should_query_postById_before_update_post()
      throws NoSuchFieldException, IllegalAccessException {

    // Given
    long postId = 1L;
    String updatedTitle = "updated title";
    String updatedContent = "updated content";

    var user = EntityFixture.getUser();
    FieldSetter.assignId(user, 1L);

    var targetPost = EntityFixture.getFirstPost();
    targetPost.assignUser(user);
    FieldSetter.assignId(targetPost, postId);

    // When
    when(postRepository.findById(postId)).thenReturn(Optional.of(targetPost));
    when(postRepository.save(targetPost)).thenReturn(targetPost);
    PostResponse upsertResult = postService.updatePost(new UpdatePostRequest(updatedTitle, updatedContent, postId, user.getId()));

    // Then
    verify(postRepository, times(1)).findById(postId);
    assertThat(upsertResult.getTitle()).isEqualTo(updatedTitle);
    assertThat(upsertResult.getContent()).isEqualTo(updatedContent);
    assertThat(upsertResult.getId()).isEqualTo(postId);

  }

  @Test
  @DisplayName("getPostById를 호출하면 PostId를 사용해서 repository의 findById를 호출해야 한다.")
  void getPostById_delegates_repository_findById()
      throws NoSuchFieldException, IllegalAccessException {

    // Given
    var postId = 1L;
    var queriedPost = EntityFixture.getFirstPost();
    var user = EntityFixture.getUser();
    queriedPost.assignUser(user);
    FieldSetter.assignId(queriedPost, postId);

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
