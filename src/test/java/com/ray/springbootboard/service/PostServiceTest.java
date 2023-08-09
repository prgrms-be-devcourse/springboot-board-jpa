package com.ray.springbootboard.service;

import com.ray.springbootboard.domain.Post;
import com.ray.springbootboard.repository.PostRepository;
import com.ray.springbootboard.service.vo.PostUpdateInfo;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class PostServiceTest {

    @Mock
    PostRepository postRepository;

    @InjectMocks
    PostServiceImpl postService;

    @Test
    @DisplayName("아이디로 포스트를 찾는데 성공한다")
    void successFindPostById() {
        // Given
        Long postId = 1L;
        Post post = new Post(postId, "title", "content", null, null);
        given(postRepository.findById(postId)).willReturn(Optional.of(post));

        // When
        Post foundPost = postService.getById(postId);

        // Then
        assertThat(post)
                .usingRecursiveComparison()
                .isEqualTo(foundPost);
    }

    @Test
    @DisplayName("아이디로 포스트를 찾는데 실패한다")
    void failFindPostByWrongId() {
        // Given
        Long wrongId = 10000L;
        given(postRepository.findById(wrongId)).willThrow(IllegalArgumentException.class);

        // When & Then
        assertThatThrownBy(() -> postService.getById(wrongId))
                .isExactlyInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("포스트 업데이트에 성공한다")
    void successUpdatePost() {
        // Given
        Long postId = 1L;
        Post post = new Post(postId, "title", "content", null, null);
        given(postRepository.findById(postId)).willReturn(Optional.of(post));
        PostUpdateInfo info = new PostUpdateInfo(1L, "title2", "content2");

        // When
        Long updatedId = postService.update(info);

        // Then
        assertThat(updatedId).isEqualTo(1L);
        assertThat(post).hasFieldOrPropertyWithValue("title", info.title());
        assertThat(post).hasFieldOrPropertyWithValue("content", info.content());
    }


    @ParameterizedTest(name = "{index} title : {0} & content : {1}")
    @MethodSource("postUpdateInfo")
    @DisplayName("포스트 업데이트에 실패한다")
    void failUpdatePost(String title, String content) {
        // Given
        Long postId = 1L;
        Post post = new Post(postId, "title", "content", null, null);
        given(postRepository.findById(postId)).willReturn(Optional.of(post));
        PostUpdateInfo info = new PostUpdateInfo(1L, title, content);

        // When & Then
        assertThatThrownBy(() -> postService.update(info))
                .isExactlyInstanceOf(IllegalArgumentException.class);
    }

    static Stream<Arguments> postUpdateInfo() {
        return Stream.of(
                Arguments.arguments(null, null),
                Arguments.arguments("title2", null),
                Arguments.arguments(null, "content2"),
                Arguments.arguments("title2", ""),
                Arguments.arguments("title2", " "),
                Arguments.arguments("", "content2"),
                Arguments.arguments(" ", "content2"),
                Arguments.arguments(" ", "content2")
        );
    }
}
