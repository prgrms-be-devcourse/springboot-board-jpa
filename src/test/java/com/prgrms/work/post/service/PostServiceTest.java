package com.prgrms.work.post.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.verify;

import com.prgrms.work.error.EntityInvalidException;
import com.prgrms.work.error.PostNotFoundException;
import com.prgrms.work.post.domain.Post;
import com.prgrms.work.post.repository.PostRepository;
import com.prgrms.work.user.domain.User;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

@DisplayName("PostService 클래스")
@ExtendWith(MockitoExtension.class)
class PostServiceTest {

    private Long VALID_ID;
    private static final Long INVALID_ID = Long.MAX_VALUE;
    private static final String TITLE = "제목";
    private static final String CONTENT = "내용내용내용내용내용";
    private static final String UPDATE_TITLE = "수정";
    private static final String UPDATE_CONTENT = "수정수정수정수정수정";
    private static final int VALID_SIZE = 10;

    @Mock
    private PostRepository postRepository;

    @InjectMocks
    private PostServiceImpl postService;

    @Nested
    @DisplayName("create 메서드는")
    class Describe_create {

        @Nested
        @DisplayName("게시글 작성 요청에 올바른 값을 전달한 사용자가 있을 경우에")
        class Context_create_request {

            Post post = createPost();

            @BeforeEach
            void setUp() {
                post = createPost();
                given(postRepository.save(post)).willReturn(post);
            }

            @Test
            @DisplayName("게시글을 저장소에 저장 후 저장된 게시글을 리턴시킵니다.")
            void It_return_savedPost() {
                Post savedPost = postService.create(post);

                assertThat(savedPost.getTitle()).isEqualTo(TITLE);
                verify(postRepository).save(any(Post.class));
            }
        }

    }

    @Nested
    @DisplayName("getPosts 메서드는")
    class Describe_getPosts {

        @Nested
        @DisplayName("사용자가 게시글 조회 요청을 한 경우에")
        class Context_getPosts {

            Page<Post> posts = createPosts();

            @BeforeEach
            void setUp() {
                given(postService.getPosts(PageRequest.of(1, 10))).willReturn(posts);
            }

            @Test
            @DisplayName("페이지 요청 개수에 맞게 게시글 페이지를 리턴합니다.")
            void It_return_posts() {
                Page<Post> posts = postService.getPosts(PageRequest.of(1, 10));

                assertThat(posts).hasSize(10);
            }
        }

    }

    @Nested
    @DisplayName("getPost 메서드는")
    class Describe_getPost {

        @Nested
        @DisplayName("게시글 상세조회 요청에 넘어온 아이디의 게시글이 존재할 경우에")
        class Context_exist_post {

            Post post = createPost();

            @BeforeEach
            void setUp() {
                given(postRepository.findById(1L)).willReturn(Optional.of(post));
            }

            @Test
            @DisplayName("조회한 게시글을 리턴합니다.")
            void It_return_post() {
                Post foundPost = postService.getPost(1L);

                assertThat(foundPost.getTitle()).isEqualTo(post.getTitle());
            }
        }

        @Nested
        @DisplayName("게시글 상세조회 요청에 넘어온 아이디가 존재하지 않는 경우에")
        class Context_not_exist_post {

            @BeforeEach
            void setUp() {
                given(postRepository.findById(INVALID_ID)).willThrow(new PostNotFoundException("게시글을 찾을 수 없습니다."));
            }

            @Test
            @DisplayName("PostNotFoundException 에외를 던진다.")
            void It_return_postNotFoundException() {
                assertThatThrownBy(() -> postService.getPost(INVALID_ID))
                    .isInstanceOf(PostNotFoundException.class);
            }

        }

    }

    @Nested
    @DisplayName("update 메서드는")
    class Describe_update {

        @Nested
        @DisplayName("게시글 수정 요청이 호출된 경우에")
        class Context_post_update {

            Post updatePost = createPost();

            @BeforeEach
            void setUp() {
                updatePost.modify(UPDATE_TITLE, UPDATE_CONTENT);
                given(postRepository.findById(1L)).willReturn(Optional.of(updatePost));
            }

            @Test
            @DisplayName("게시글 제목과 내용을 수정합니다.")
            void It_modify_post() {
                postService.update(1L, UPDATE_TITLE, UPDATE_CONTENT);
                Post update = postService.getPost(1L);

                assertThat(update.getTitle()).isEqualTo(UPDATE_TITLE);
            }

        }

    }

    @Nested
    @DisplayName("delete 메서드는")
    class Describe_delete {

        @Nested
        @DisplayName("삭제 요청된 게시글 아이디가 존재할 경우에")
        class Context_exist_post {

            @BeforeEach
            void setUp() {
                willDoNothing().given(postRepository).deleteById(1L);
                given(postRepository.findById(1L)).willThrow(new PostNotFoundException("게시글을 찾을 수 없습니다."));
            }

            @Test
            @DisplayName("저장소에서 해당 게시글을 삭제합니다.")
            void It_delete_post() {
                postService.delete(1L);

                verify(postRepository).deleteById(1L);
                assertThatThrownBy(() -> {
                    postService.getPost(1L);
                }).isInstanceOf(PostNotFoundException.class);
            }

        }

    }

    private static Post createPost() {
        User user = createUser();
        return Post.create(user.getName(), TITLE, CONTENT, user);
    }

    private static User createUser() {
        return User.create("김형욱", 27, "산책");
    }

    private static Page<Post> createPosts() {
        List<Post> posts = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            User user = createUser();
            posts.add(Post.create(user.getName(), TITLE + i, CONTENT + i, user));
        }

        return new PageImpl<Post>(posts);
    }

}