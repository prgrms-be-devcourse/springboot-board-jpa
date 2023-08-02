package com.kdt.board.domain.post.service;

import com.kdt.board.domain.post.dto.PostRequestDto;
import com.kdt.board.domain.post.entity.Post;
import com.kdt.board.domain.post.repository.PostRepository;
import com.kdt.board.domain.user.entity.User;
import com.kdt.board.domain.user.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PostServiceTest {

    @InjectMocks
    private PostService postService;
    @Mock
    private PostRepository postRepository;
    @Mock
    private UserRepository userRepository;

    User user;

    @BeforeEach
    void setUp() {
        user = User.builder()
                .id(1L)
                .name("beomu")
                .age(45)
                .hobby("축구")
                .build();
    }

    @Test
    @DisplayName("유저를 저장하면 성공한다")
    void test_save_user() {
        String title = "게시글 제목";
        String content = "게시글 내용";

        Post post = Post.builder()
                .title(title)
                .content(content)
                .user(user)
                .build();

        PostRequestDto dto = PostRequestDto.builder()
                .title(title)
                .content(content)
                .userId(user.getId())
                .build();

        when(postRepository.save(any())).thenReturn(post);
        when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));


        Post savedPost = postService.save(dto);

        verify(postRepository).save(any());

        assertThat(savedPost.getTitle()).isEqualTo(title);
        assertThat(savedPost.getContent()).isEqualTo(content);
    }
}
