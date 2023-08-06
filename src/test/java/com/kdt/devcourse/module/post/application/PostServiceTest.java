package com.kdt.devcourse.module.post.application;

import com.kdt.devcourse.module.post.domain.Post;
import com.kdt.devcourse.module.post.domain.repository.PostRepository;
import com.kdt.devcourse.module.post.presentation.dto.CreateOrUpdatePost;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.any;
import static org.mockito.BDDMockito.then;
import static org.mockito.BDDMockito.times;
import static org.mockito.BDDMockito.willReturn;

@ExtendWith(SpringExtension.class)
class PostServiceTest {
    @InjectMocks
    private PostService postService;

    @Mock
    private PostRepository postRepository;

    @Test
    @DisplayName("반환되는 응답의 내용이 동일해야 한다.")
    void getPostResponse_Success() {
        // given
        String title = "title";
        String content = "content";
        Post post = new Post(title, content);
        willReturn(Optional.of(post)).given(postRepository).findById(any());

        // when
        CreateOrUpdatePost.Response postResponse = postService.getPostResponse(1L);

        // then
        then(postRepository).should(times(1)).findById(any());
        assertThat(postResponse.title()).isEqualTo(title);
        assertThat(postResponse.content()).isEqualTo(content);
    }
}
