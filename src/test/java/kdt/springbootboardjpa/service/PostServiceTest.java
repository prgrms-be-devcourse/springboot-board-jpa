package kdt.springbootboardjpa.service;

import kdt.springbootboardjpa.respository.PostRepository;
import kdt.springbootboardjpa.respository.UserRepository;
import kdt.springbootboardjpa.respository.entity.Post;
import kdt.springbootboardjpa.respository.entity.User;
import kdt.springbootboardjpa.service.dto.PostDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class PostServiceTest {

    PostRepository postRepository = mock(PostRepository.class);
    UserRepository userRepository = mock(UserRepository.class);
    PostService postService = new PostService(userRepository, postRepository);

    @Test
    @DisplayName("[성공] Post Id로 Post 조회하기")
    void testGetPostById() {
        User user = User.builder()
                .id(1L)
                .name("name")
                .age("34")
                .hobby("hobby")
                .build();
        Post post = Post.builder()
                .id(1L)
                .title("title")
                .content("content")
                .user(user)
                .build();
        when(postRepository.findById(1L))
                .thenReturn(Optional.of(post));

        Post returnedPost = postService.getPostById(1L);

        assertThat(returnedPost)
                .usingRecursiveComparison()
                .isEqualTo(post);
    }

    @Test
    @DisplayName("[실패] Post Id로 Post 조회하기, Post Id가 존재하지 않는 경우")
    void testGetPostById_invalidPost() {
        when(postRepository.findById(1L))
                .thenReturn(Optional.empty());

        assertThatThrownBy(
                () -> postService.getPostById(1L)
        ).isInstanceOf(RuntimeException.class)
                .hasMessageContaining("Can't find");
    }

    @Test
    @DisplayName("[성공] Post 생성하기")
    void testCreatePost() {
        User user = User.builder()
                .id(1L)
                .name("name")
                .age("34")
                .hobby("hobby")
                .build();
        Post post = Post.builder()
                .title("title")
                .content("content")
                .user(user)
                .build();
        when(userRepository.findById(1L))
                .thenReturn(Optional.of(user));
        when(postRepository.save(any()))
                .thenReturn(post);

        PostDto postDto = PostDto.builder()
                .title("title")
                .content("content")
                .createdBy(1L)
                .build();
        Post savedPost = postService.createPost(postDto);

        assertThat(savedPost.getTitle()).isEqualTo(post.getTitle());
        assertThat(savedPost.getUser().getName()).isEqualTo(user.getName());
    }

    @Test
    @DisplayName("[실패] Post 생성하기, User가 존재하지 않는 경우")
    void testCreatePost_invalidUser() {
        PostDto postDto = PostDto.builder()
                .title("title")
                .content("content")
                .createdBy(1L)
                .build();
        when(userRepository.findById(1L))
                .thenReturn(Optional.empty());

        assertThatThrownBy(
                () -> postService.createPost(postDto)
        ).isInstanceOf(RuntimeException.class)
                .hasMessageContaining("Can't find");
    }

    @Test
    @DisplayName("[성공] Post 수정하기")
    void testUpdatePost() {
        User user = User.builder()
                .id(1L)
                .name("name")
                .age("34")
                .hobby("hobby")
                .build();
        Post post = Post.builder()
                .title("title")
                .content("content")
                .user(user)
                .build();
        when(postRepository.findById(1L))
                .thenReturn(Optional.of(post));
        when(postRepository.save(any()))
                .thenReturn(post);

        PostDto postDto = PostDto.builder()
                .title("title")
                .content("content")
                .createdBy(1L)
                .build();
        Post savedPost = postService.updatePost(1L, postDto);

        assertThat(savedPost.getTitle()).isEqualTo(post.getTitle());
        assertThat(savedPost.getUser().getName()).isEqualTo(user.getName());
    }

    @Test
    @DisplayName("[성공] Post 수정하기, Post가 존재하지 않는 경우")
    void testUpdatePost_invalidId() {
        PostDto postDto = PostDto.builder()
                .title("title")
                .content("content")
                .createdBy(1L)
                .build();
        when(postRepository.findById(1L))
                .thenReturn(Optional.empty());

        assertThatThrownBy(
                () -> postService.updatePost(postDto.id(), postDto)
        ).isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Can't find");
    }
}
