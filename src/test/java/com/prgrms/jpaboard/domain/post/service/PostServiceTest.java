package com.prgrms.jpaboard.domain.post.service;

import com.prgrms.jpaboard.domain.post.domain.Post;
import com.prgrms.jpaboard.domain.post.domain.PostRepository;
import com.prgrms.jpaboard.domain.post.dto.request.PostCreateDto;
import com.prgrms.jpaboard.domain.post.dto.request.PostUpdateDto;
import com.prgrms.jpaboard.domain.post.exception.PostNotFoundException;
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

    private PostCreateDto postCreateDto = new PostCreateDto(1L, "제목", "내용");

    private User user = User.builder()
            .name("jerry")
            .age(25)
            .hobby("누워있기")
            .createdBy("jerry")
            .build();

    private Post post = Post.builder()
            .title("test title")
            .content("this is content of test")
            .createdBy("1")
            .build();

    private PostUpdateDto postUpdateDto = new PostUpdateDto("수정된 제목", "수정된 내용");

    @Test
    @DisplayName("게시글 작성 예외 테스트")
    void testCreatePostException() {
        when(userRepository.findById(postCreateDto.getUserId())).thenThrow(new UserNotFoundException());

        try{
            postService.createPost(postCreateDto);
        }catch (UserNotFoundException e) {
            verify(postRepository, never()).save(any());
        }
    }

    @Test
    @DisplayName("게시글 작성 테스트")
    void testCreatePost() {
        when(userRepository.findById(postCreateDto.getUserId())).thenReturn(Optional.of(user));
        when(postRepository.save(any())).thenReturn(post);

        postService.createPost(postCreateDto);

        verify(postRepository).save(any());
    }
}