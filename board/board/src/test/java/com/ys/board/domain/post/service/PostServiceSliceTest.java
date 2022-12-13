package com.ys.board.domain.post.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.verify;

import com.ys.board.common.exception.EntityNotFoundException;
import com.ys.board.domain.post.Post;
import com.ys.board.domain.post.api.PostCreateRequest;
import com.ys.board.domain.post.repository.PostRepository;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class PostServiceSliceTest {

    @InjectMocks
    private PostService postService;

    @Mock
    private PostRepository postRepository;

    @DisplayName("createPost 성공 테스트 - Post가 생성된다.")
    @Test
    void createPostSuccess() {
        //given
        String title = "title";
        String content = "content";
        long userId = 1L;
        PostCreateRequest postCreateRequest = new PostCreateRequest(title, content, userId);
        Post post = Post.create(title, content);

        given(postRepository.save(post))
            .willReturn(post);

        try (MockedStatic<Post> postMockedStatic = mockStatic(Post.class)) {
            given(Post.create(title, content))
                .willReturn(post);

            //when
            Post savedPost = postService.createPost(postCreateRequest);

            //then
            assertEquals(post, savedPost);
            verify(postRepository).save(post);
            postMockedStatic.verify(() -> Post.create(title, content));

        }

    }

    @DisplayName("findById 조회 성공 테스트 - Post 가 조회된다.")
    @Test
    void findByIdPostSuccess() {
        //given
        String title = "title";
        String content = "content";
        long postId = 1L;
        Post post = Post.create(title, content);

        given(postRepository.findById(postId))
            .willReturn(Optional.of(post));

        //when
        Post savedPost = postService.findById(postId);

        //then
        assertEquals(post, savedPost);
        verify(postRepository).findById(postId);
    }

    @DisplayName("findById 조회 실패 테스트 - Post 가 없으므로 예외를 던진다.")
    @Test
    void findByIdPostFailNotFound() {
        //given
        long postId = 1L;
        given(postRepository.findById(postId))
            .willReturn(Optional.empty());

        //when & then
        assertThrows(EntityNotFoundException.class, () -> postService.findById(postId));

        verify(postRepository).findById(postId);
    }

}