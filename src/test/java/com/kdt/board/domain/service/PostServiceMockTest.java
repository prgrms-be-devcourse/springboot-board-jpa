package com.kdt.board.domain.service;

import com.kdt.board.domain.converter.Converter;
import com.kdt.board.domain.dto.PostDto;
import com.kdt.board.domain.dto.UserDto;
import com.kdt.board.domain.model.Post;
import com.kdt.board.domain.model.User;
import com.kdt.board.domain.repository.PostRepository;
import com.kdt.board.domain.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PostServiceMockTest {
    @InjectMocks
    PostService postService;

    @Mock
    PostRepository postRepository;

    @Mock
    UserRepository userRepository;

    @Mock
    Converter converter;

    User user;
    Post post;

    @BeforeEach
    void setUp() {
        Long testId = 1L;
        String testTitle = "제목";
        String testContent = "내용";

        user = User.builder()
                .id(1L)
                .age(26)
                .name("이용훈")
                .hobby("Tennis")
                .build();

        post = Post.builder()
                .id(testId)
                .user(user)
                .title(testTitle)
                .content(testContent)
                .build();
    }

    @Test
    @DisplayName("포스트 등록 확인")
    void savePostTest() {
        // given
        Long testId = 1L;
        String testTitle = "제목";
        String testContent = "내용";

        PostDto.SaveRequest postRequest = new PostDto.SaveRequest(testTitle, testContent, 1L);
        doReturn(post).when(converter).convertPost(postRequest);
        doReturn(post).when(postRepository).save(any(Post.class));

        // when
        Long savedId = postService.save(postRequest);

        // then
        verify(postRepository, times(1)).save(any(Post.class));
        verify(converter, times(1)).convertPost(any(PostDto.SaveRequest.class));
        assertThat(savedId).isEqualTo(testId);
    }

    @Test
    @DisplayName("포스트 수정 확인")
    void updatePostTest() {
        // given
        Long testId = 1L;
        String testTitle = "제목";
        String testContent = "내용";

        PostDto.UpdateRequest postUpdate = new PostDto.UpdateRequest(testId, testTitle, testContent, 1L);
        doReturn(Optional.of(post)).when(postRepository).findById(postUpdate.id());
        UserDto.Response userResponse = new UserDto.Response(1L, "이용훈", 26, "Tennis", LocalDateTime.now(), LocalDateTime.now());

        PostDto.Response postResponse = new PostDto.Response(testId, testTitle, testContent, userResponse, LocalDateTime.now(), LocalDateTime.now());
        doReturn(postResponse).when(converter).convertPostDto(post);

        // when
        PostDto.Response updatedPostDto = postService.update(postUpdate);

        // then
        assertThat(updatedPostDto.id()).isEqualTo(testId);
        assertThat(updatedPostDto.title()).isEqualTo(testTitle);
        assertThat(updatedPostDto.content()).isEqualTo(testContent);
        assertThat(updatedPostDto.user()).isEqualTo(userResponse);
    }
}
