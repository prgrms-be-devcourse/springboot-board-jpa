package com.prgrms.jpaboard.domain.post.service;

import com.prgrms.jpaboard.domain.post.domain.Post;
import com.prgrms.jpaboard.domain.post.domain.PostRepository;
import com.prgrms.jpaboard.domain.post.dto.PostRequestDto;
import com.prgrms.jpaboard.domain.user.domain.User;
import com.prgrms.jpaboard.domain.user.domain.UserRepository;
import com.prgrms.jpaboard.domain.user.exception.UserNotFoundException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PostServiceTest {
    @InjectMocks
    private PostService postService;

    @Mock
    private PostRepository postRepository;

    @Mock
    private UserRepository userRepository;

    private PostRequestDto postRequestDto = new PostRequestDto(1L, "제목", "내용");

    private User user = User.builder()
            .id(1L)
            .build();

    private Post post = Post.builder()
            .id(1L)
            .build();

    @Test
    @DisplayName("게시글 작성 예외 테스트")
    void testCreatePostException() {
        when(userRepository.findById(postRequestDto.getUserId())).thenThrow(new UserNotFoundException());

        try{
            postService.createPost(postRequestDto);
        }catch (UserNotFoundException e) {
            verify(postRepository, never()).save(any());
        }
    }

    @Test
    @DisplayName("게시글 작성 테스트")
    void testCreatePost() {
        when(userRepository.findById(postRequestDto.getUserId())).thenReturn(Optional.of(user));
        when(postRepository.save(any())).thenReturn(post);

        postService.createPost(postRequestDto);

        verify(postRepository).save(any());
    }
}