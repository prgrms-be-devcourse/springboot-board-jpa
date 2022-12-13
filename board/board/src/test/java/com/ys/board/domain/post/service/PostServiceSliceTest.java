package com.ys.board.domain.post.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.verify;

import com.ys.board.domain.post.Post;
import com.ys.board.domain.post.api.PostCreateRequest;
import com.ys.board.domain.post.repository.PostRepository;
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

}