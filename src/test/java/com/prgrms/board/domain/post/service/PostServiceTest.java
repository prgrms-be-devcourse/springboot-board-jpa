package com.prgrms.board.domain.post.service;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.*;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import com.prgrms.board.domain.post.dto.request.PostCreateRequest;
import com.prgrms.board.domain.post.dto.response.PostResponse;
import com.prgrms.board.domain.post.entity.Post;
import com.prgrms.board.domain.post.repository.PostRepository;
import com.prgrms.board.domain.user.entity.User;
import com.prgrms.board.domain.user.service.UserService;
import com.prgrms.board.global.common.PageResponse;
import com.prgrms.board.support.PostFixture;
import com.prgrms.board.support.UserFixture;

@ExtendWith(MockitoExtension.class)
class PostServiceTest {

    @InjectMocks
    private PostService postService;

    @Mock
    private PostRepository postRepository;

    @Mock
    private UserService userService;

    private User user;

    @BeforeEach
    void init() {
        user = UserFixture.create();
    }

    @Test
    @DisplayName("게시물 생성에 성공한다.")
    void create_post_success() {
        // given
        PostCreateRequest request = new PostCreateRequest(user.getId(), "제목", "내용");
        Post post = PostFixture.create(user, "제목", "내용");
        given(postRepository.save(any(Post.class))).willReturn(post);

        // when
        PostResponse result = postService.createPost(request);

        // then
        assertThat(result.title()).isEqualTo("제목");
    }

    @Test
    @DisplayName("전체 게시물 조회에 성공한다.")
    void get_posts_success() {
        // given
        Post post1 = PostFixture.create(user, "제목1", "내용1");
        Post post2 = PostFixture.create(user, "제목2", "내용2");
        Post post3 = PostFixture.create(user, "제목3", "내용3");
        List<Post> posts = List.of(post1, post2, post3);

        Pageable pageable = PageRequest.of(0, 2);
        Page<Post> page = new PageImpl<>(posts.subList(0, 2), pageable, posts.size());
        given(postRepository.findAll(any(Pageable.class))).willReturn(page);

        // when
        PageResponse<Post> result = postService.getPosts(pageable);

        // then
        assertThat(result.totalCount()).isEqualTo(3);
        assertThat(result.items()).hasSize(2);
    }
}
