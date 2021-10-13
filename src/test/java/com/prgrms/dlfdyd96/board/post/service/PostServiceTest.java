package com.prgrms.dlfdyd96.board.post.service;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.prgrms.dlfdyd96.board.domain.Post;
import com.prgrms.dlfdyd96.board.domain.User;
import com.prgrms.dlfdyd96.board.post.converter.PostConverter;
import com.prgrms.dlfdyd96.board.post.dto.CreatePostRequest;
import com.prgrms.dlfdyd96.board.post.dto.PostResponse;
import com.prgrms.dlfdyd96.board.post.dto.UpdatePostRequest;
import com.prgrms.dlfdyd96.board.post.repository.PostRepository;
import com.prgrms.dlfdyd96.board.user.repository.UserRepository;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import javassist.NotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

@Slf4j
@ExtendWith(MockitoExtension.class)
class PostServiceTest {

  @InjectMocks
  private PostService postService;

  @Mock
  private PostRepository postRepository;
  @Mock
  private UserRepository userRepository;
  @Mock
  private PostConverter postConverter;

  private User user;

  @BeforeEach
  void setUp() {
    user = User.builder()
        .id(1L)
        .name("yong")
        .age(11)
        .hobby("running")
        .build();
  }

  @Test
  @DisplayName("[POST] 게시물을 생성할 수 있다. ")
  void testSave() throws NotFoundException {
    // GIVEN
    CreatePostRequest givenRequest = CreatePostRequest.builder()
        .title("제목")
        .content("내용입니다. ")
        .userId(user.getId())
        .build();
    Post stub = Post.builder()
        .title(givenRequest.getTitle())
        .content(givenRequest.getContent())
        .user(user)
        .build();
    Post result = Post.builder()
        .id(1L)
        .title(stub.getTitle())
        .content(stub.getContent())
        .user(user)
        .build();

    when(userRepository.findById(givenRequest.getUserId())).thenReturn(Optional.of(user));
    when(postConverter.convertPost(givenRequest, user)).thenReturn(stub);
    when(postRepository.save(any())).thenReturn(result);
    // WHEN
    Long savedPostId = postService.save(givenRequest);

    // THEN
    assertThat(savedPostId).isEqualTo(result.getId());
    verify(postConverter).convertPost(givenRequest, user);
    verify(postRepository).save(stub);
  }

  @Test
  @DisplayName("[POST] 게시물을 생성하기 위해 user가 유효해야 한다.")
  void testSaveException() {
    // GIVEN
    Long wrongUserId = 2L;
    CreatePostRequest givenRequest = CreatePostRequest.builder()
        .title("제목")
        .content("내용입니다. ")
        .userId(wrongUserId)
        .build();

    when(userRepository.findById(givenRequest.getUserId())).thenReturn(Optional.empty());
    // WHEN
    // THEN
    assertThatThrownBy(
        () -> postService.save(givenRequest)
    ).isInstanceOf(NotFoundException.class);
  }

  @Test
  @DisplayName("[GET] 게시물 단건 조회할 수 있다.")
  void testFindOne() throws NotFoundException {
    // GIVEN
    Long givenPostId = 1L;
    Post postStub = Post.builder()
        .id(givenPostId)
        .title("제목")
        .content("내용입니다. ")
        .user(user)
        .build();
    PostResponse postResponseStub = PostResponse.builder()
        .id(postStub.getId())
        .title(postStub.getTitle())
        .content(postStub.getContent())
        .userName(postStub.getUser().getName())
        .build();
    when(postRepository.findById(givenPostId)).thenReturn(Optional.of(postStub));
    when(postConverter.convertPostResponse(postStub)).thenReturn(postResponseStub);
    // WHEN
    PostResponse result = postService.findOne(givenPostId);

    // THEN
    assertThat(result).isEqualTo(postResponseStub);
    verify(postRepository).findById(givenPostId);
    verify(postConverter).convertPostResponse(postStub);
  }

  @Test
  @DisplayName("[GET] 없는 게시물 단건 조회할 시 NotFoundException을 발생한다.")
  void testFindOneException() {
    // GIVEN
    Long givenWrongPostId = 1L;
    when(postRepository.findById(givenWrongPostId)).thenReturn(Optional.empty());
    // WHEN
    // THEN
    assertThatThrownBy(() -> postService.findOne(givenWrongPostId))
        .isInstanceOf(NotFoundException.class);
    verify(postRepository).findById(givenWrongPostId);
  }

  @Test
  @DisplayName("[GET] 게시물 리스트를 조회할 수 있다.")
  void testFindPosts() {
    // GIVEN
    int givenPage = 0;
    int givenSize = 2;
    Pageable givenPageable = PageRequest.of(givenPage, givenSize);
    List<Post> stubPosts = List.of(
        Post.builder()
            .id(1L)
            .title("제목1")
            .content("내용1입니다. ")
            .user(user)
            .build(),
        Post.builder()
            .id(2L)
            .title("제목1")
            .content("내용2입니다. ")
            .user(user)
            .build()
    );
    Page<Post> stubFindAll = new PageImpl<>(stubPosts);
    Page<PostResponse> stubConvertedFindAll = stubFindAll.map((item) -> PostResponse.builder()
        .id(item.getId())
        .userName(item.getUser().getName())
        .title(item.getTitle())
        .content(item.getContent())
        .build());
    when(postRepository.findAll(givenPageable)).thenReturn(stubFindAll);
    stubConvertedFindAll.forEach(item ->
        when(postConverter.convertPostResponse(any())).thenReturn(item) // TODO : ??
    );

    // WHEN
    Page<PostResponse> result = postService.findPosts(givenPageable);

    // THEN
    assertThat(result.getTotalElements()).isEqualTo(stubConvertedFindAll.getTotalElements());
    assertThat(result.getTotalPages()).isEqualTo(stubConvertedFindAll.getTotalPages());
    verify(postRepository).findAll(givenPageable);
    stubFindAll.forEach(item -> verify(postConverter).convertPostResponse(item));
  }

  @Test
  @DisplayName("[PUT] 게시물을 수정할 수 있다.")
  void testUpdate() throws NotFoundException {
    // GIVEN
    Post originPost = Post.builder()
        .id(1L)
        .title("제목1")
        .content("내용1입니다. ")
        .user(user)
        .build();
    Long givenPostId = originPost.getId();
    UpdatePostRequest givenRequest = UpdatePostRequest.builder()
        .content("수정할 내용")
        .title("수정할 제목")
        .userId(user.getId())
        .build();
    PostResponse stubConvertedPost = PostResponse.builder()
        .id(originPost.getId())
        .userName(originPost.getUser().getName())
        .title(givenRequest.getTitle())
        .content(givenRequest.getContent())
        .build();
    when(postRepository.findById(givenPostId)).thenReturn(Optional.of(originPost));
    when(postConverter.convertPostResponse(originPost)).thenReturn(stubConvertedPost);
    when(userRepository.findById(givenRequest.getUserId())).thenReturn(Optional.of(user));
    // WHEN
    PostResponse result = postService.update(givenPostId, givenRequest);

    // THEN
    assertThat(result).isEqualTo(stubConvertedPost);
    verify(postRepository).findById(givenPostId);
    verify(postConverter).convertPostResponse(originPost);

  }

  @Test
  @DisplayName("[PUT] 수정할 게시물을 찾을 수 없으면 NotFoundException을 발생한다.")
  void testUpdatePostNotFoundException() {
    // GIVEN
    Long givenNotMatchedPostId = 2L;
    UpdatePostRequest givenRequest = UpdatePostRequest.builder()
        .content("수정할 내용")
        .title("수정할 제목")
        .userId(user.getId())
        .build();
    when(postRepository.findById(givenNotMatchedPostId)).thenReturn(Optional.empty());

    // WHEN
    // THEN
    assertThatThrownBy(() -> postService.update(givenNotMatchedPostId, givenRequest))
        .isInstanceOf(NotFoundException.class);
    verify(postRepository).findById(givenNotMatchedPostId);
  }

  @Test
  @DisplayName("[PUT] 수정할 게시물이 사용자가 다르면 NotFoundException을 발생한다.(공격 방어)")
  void testUpdateUserNotMatchException() {
    // GIVEN
    Post originPost = Post.builder()
        .id(1L)
        .title("제목1")
        .content("내용1입니다. ")
        .user(user)
        .build();

    Long givenCorrectPostId = originPost.getId();
    Long givenNotMatchUserId = user.getId() + 1;

    UpdatePostRequest givenRequest = UpdatePostRequest.builder()
        .content("수정할 내용")
        .title("수정할 제목")
        .userId(givenNotMatchUserId)
        .build();
    when(postRepository.findById(givenCorrectPostId)).thenReturn(Optional.of(originPost));
    when(userRepository.findById(givenNotMatchUserId)).thenReturn(Optional.empty());

    // WHEN
    // THEN
    assertThatThrownBy(() -> postService.update(givenCorrectPostId, givenRequest))
        .isInstanceOf(NotFoundException.class);
    verify(postRepository).findById(givenCorrectPostId);
    verify(userRepository).findById(givenNotMatchUserId);
  }

  @Test
  @DisplayName("[PUT] 수정할 게시물이 사용자가 다르면 NotFoundException을 발생한다.(공격 방어)")
  void testDelete() throws NotFoundException {
    // GIVEN
    Post originPost = Post.builder()
        .id(1L)
        .title("제목1")
        .content("내용1입니다. ")
        .user(user)
        .build();
    Long givenPostId = originPost.getId();
    when(postRepository.findById(givenPostId)).thenReturn(Optional.of(originPost));

    // WHEN
    postService.delete(givenPostId);

    // THEN
    verify(postRepository).findById(givenPostId);

  }

  @Test
  @DisplayName("[PUT] 수정할 게시물이 사용자가 다르면 NotFoundException을 발생한다.(공격 방어)")
  void testDeleteNotFoundException() {
    // GIVEN
    Long givenWrongPostId = 0L;
    when(postRepository.findById(givenWrongPostId)).thenReturn(Optional.empty());

    // WHEN
    // THEN
    assertThatThrownBy(
        () -> postService.delete(givenWrongPostId)
    ).isInstanceOf(NotFoundException.class);
    verify(postRepository).findById(givenWrongPostId);
  }


}