package com.prgrms.be.app.service;

import com.prgrms.be.app.domain.Post;
import com.prgrms.be.app.domain.User;
import com.prgrms.be.app.domain.dto.PostDTO;
import com.prgrms.be.app.repository.PostRepository;
import com.prgrms.be.app.repository.UserRepository;
import com.prgrms.be.app.util.PostConverter;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@Slf4j
class PostServiceTest {

    private final PostRepository postRepository = mock(PostRepository.class);
    private final UserRepository userRepository = mock(UserRepository.class);
    private final PostConverter postConverter = spy(PostConverter.class);
    private final PostService postService = new PostService(postRepository, userRepository, postConverter);

    @Test
    @DisplayName("게시글 생성 시 존재하지 않는 유저 id가 들어올 경우 예외가 발생한다.")
    void wrongUserIdTest() {
        // given
        PostDTO.CreateRequest postCreateDTO = new PostDTO.CreateRequest("title", "content", 1L);
        // when
        when(userRepository.findById(anyLong()))
                .thenReturn(Optional.empty());
        // then
        assertThrows(EntityNotFoundException.class, () -> postService.createPost(postCreateDTO));
    }

    @Test
    @DisplayName("게시글을 생성할 수 있다.")
    void shouldCreatePost() {
        // given
        PostDTO.CreateRequest postCreateDTO = new PostDTO.CreateRequest("title", "content", 1L);
        User user = new User("user", 25, "농구");
        Post post = new Post("title", "content", user);

        when(postConverter.covertToPost(postCreateDTO, user))
                .thenReturn(post);
        when(postRepository.save(post))
                .thenReturn(post);
        when(userRepository.findById(anyLong()))
                .thenReturn(Optional.of(user));

        // when
        Long postId = postService.createPost(postCreateDTO);

        // then
        assertThat(postId).isEqualTo(post.getId());
    }

    @Test
    @DisplayName("Post를 페이지 단위로 조회할 수 있다.")
    void shouldFindPostsInPage() {
        // given
        List<Post> posts = new ArrayList<>();
        for (long id = 1L; id <= 20; id++) {
            posts.add(new Post("title", "content", new User("user", 25, "농구")));
        }

        Pageable pageable = PageRequest.of(0, 5);
        Page<Post> postPage = new PageImpl<>(posts, pageable, posts.size());

        // when
        when(postRepository.findAll(pageable)).thenReturn(postPage);
        Page<PostDTO.PostsResponse> postDtos = postService.findAll(pageable);

        // then
        verify(postRepository).findAll(pageable);
        assertThat(postDtos.getTotalPages()).isEqualTo(4);
    }

    @Test
    @DisplayName("잘못된 게시글 id로 단건 조회 시 예외가 발생한다.")
    void wrongPostIdTest() {
        // given, when
        when(postRepository.findById(anyLong()))
                .thenReturn(Optional.empty());
        // then
        assertThrows(EntityNotFoundException.class, () -> postService.findById(1L));
    }

    @Test
    @DisplayName("게시글 id로 단건 조회 할 수 있다.")
    void shouldFindPostById() {
        // given
        PostDTO.CreateRequest postCreateDTO = new PostDTO.CreateRequest("title", "content", 1L);
        User user = new User("user", 25, "농구");
        Post post = new Post("title", "content", user);

        when(postRepository.findById(anyLong()))
                .thenReturn(Optional.of(post));
        // when
        PostDTO.PostDetailResponse postDetailResponse = postService.findById(1L);
        // then
        assertThat(postDetailResponse).isEqualTo(
                new PostDTO.PostDetailResponse(
                        post.getTitle(),
                        post.getContent(),
                        post.getId(),
                        post.getCreatedAt(),
                        post.getCreatedBy().getId(),
                        post.getCreatedBy().getName()
                )
        );
    }

    @Test
    @DisplayName("잘못된 게시글 id로 수정 시 예외가 발생한다.")
    void wrongPostIdUpdateTest() {
        // given
        PostDTO.UpdateRequest postUpdateRequest = new PostDTO.UpdateRequest("change title", "change content");
        // when
        when(postRepository.findById(anyLong()))
                .thenReturn(Optional.empty());
        // then
        assertThrows(EntityNotFoundException.class, () -> postService.updatePost(1L, postUpdateRequest));
    }


    @Test
    @DisplayName("게시글 수정을 할 수 있다.")
    void shouldUpdatePost() {
        // given
        User user = new User("user", 25, "농구");
        Post post = new Post("title", "content", user);
        PostDTO.UpdateRequest postUpdateRequest = new PostDTO.UpdateRequest("change title", "change content");
        Post updatedPost = new Post(postUpdateRequest.getTitle(), postUpdateRequest.getContent(), user);

        when(postRepository.findById(anyLong()))
                .thenReturn(Optional.of(post));

        PostDTO.PostDetailResponse beforePostDTO = postService.findById(anyLong());

        when(postRepository.findById(anyLong()))
                .thenReturn(Optional.of(updatedPost));

        Long updatedPostId = postService.updatePost(anyLong(), postUpdateRequest);

        // when
        when(postRepository.findById(updatedPostId))
                .thenReturn(Optional.of(updatedPost));
        PostDTO.PostDetailResponse updatedPostDto = postService.findById(updatedPostId);

        // then
        assertThat(beforePostDTO).isNotEqualTo(updatedPostDto);
        assertThat(postUpdateRequest.getTitle()).isEqualTo(updatedPostDto.title());
        assertThat(postUpdateRequest.getContent()).isEqualTo(updatedPostDto.content());
    }
}