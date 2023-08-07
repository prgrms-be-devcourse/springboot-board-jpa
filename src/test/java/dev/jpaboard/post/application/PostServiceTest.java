package dev.jpaboard.post.application;

import dev.jpaboard.common.exception.ForbiddenException;
import dev.jpaboard.post.domain.Post;
import dev.jpaboard.post.dto.PostCreateRequest;
import dev.jpaboard.post.dto.PostResponse;
import dev.jpaboard.post.dto.PostUpdateRequest;
import dev.jpaboard.post.dto.PostsResponse;
import dev.jpaboard.post.exception.PostNotFoundException;
import dev.jpaboard.post.repository.PostRepository;
import dev.jpaboard.user.domain.User;
import dev.jpaboard.user.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertFalse;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
class PostServiceTest {

    private static final String 제목 = "제목";
    private static final String 내용 = "내용";

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PostService postService;

    @Autowired
    private PostRepository postRepository;

    private User user;
    private Post post;

    @BeforeEach
    void setUp() {
        postRepository.deleteAll();
        userRepository.deleteAll();
        user = savedUser();
        post = savedPost(user);
    }

    @DisplayName("유저가 존재할 경우 게시물을 생성할 수 있다.")
    @Test
    void createPostTest() {
        Long userId = user.getId();

        PostCreateRequest request = new PostCreateRequest(제목, 내용);

        PostResponse response = postService.create(request, userId);

        assertAll(
                () -> assertThat(response.title()).isEqualTo(제목),
                () -> assertThat(response.content()).isEqualTo(내용)
        );
    }

    @DisplayName("게시물을 생성한 유저 아이디와 수정할 유저 아이디가 같을 경우 게시물을 수정할 수 있다.")
    @Test
    void updatePostTest() {
        Long userId = user.getId();
        Long postId = post.getId();

        postService.update(postId, new PostUpdateRequest(제목, 내용), userId);

        Post findPost = postRepository.findById(postId).get();
        assertAll(
                () -> assertThat(findPost.getTitle()).isEqualTo(제목),
                () -> assertThat(findPost.getContent()).isEqualTo(내용)
        );
    }

    @DisplayName("존재하는 게시물 아이디를 입력할 경우 게시물을 조회할 수 있다.")
    @Test
    void findPostTest() {
        Long postId = post.getId();

        PostResponse findPost = postService.findPost(postId);

        assertAll(
                () -> assertThat(findPost.title()).isEqualTo(제목),
                () -> assertThat(findPost.content()).isEqualTo(내용)
        );
    }

    @DisplayName("모든 게시물을 페이징으로 조회할 수 있다.")
    @Test
    void findAllTest() {
        Long userId = user.getId();

        PostCreateRequest request1 = new PostCreateRequest("제목", "내용");
        PostCreateRequest request2 = new PostCreateRequest("제목2", "내용2");
        PostCreateRequest request3 = new PostCreateRequest("제목3", "내용3");
        postService.create(request1, userId);
        postService.create(request2, userId);
        postService.create(request3, userId);

        PostsResponse allPosts = postService.findAll(PageRequest.of(0, 2));

        assertThat(allPosts.totalPages()).isEqualTo(2);
    }

    @DisplayName("존재하는 게시물 아이디와 해당 게시물을 생성한 유저 아이디를 입력할 경우 게시물을 삭제할 수 있다.")
    @Test
    void deletePostTest() {
        Long userId = user.getId();
        Long postId = post.getId();
        postService.delete(postId, userId);

        Optional<Post> findPost = postRepository.findById(postId);

        assertFalse(findPost.isPresent());
    }

    @DisplayName("해당 게시물을 생성한 유저 아이디와 다른 유저 아이디를 입력할 경우 게시물을 수정할 수 없다.")
    @Test
    void updateFailByUserIdTest() {
        Long postId = post.getId();

        assertThatThrownBy(() -> postService.update(postId, new PostUpdateRequest("title", "content"), 102938297928461204L))
                .isInstanceOf(ForbiddenException.class);
    }

    @DisplayName("존재하지 않는 게시물 아이디를 입력할 경우 게시물을 수정할 수 없다.")
    @Test
    void updateFailByPostIdTest() {
        Long userId = user.getId();

        assertThatThrownBy(() -> postService.update(1230984576L, new PostUpdateRequest("title", "content"), userId))
                .isInstanceOf(PostNotFoundException.class);
    }

    private User savedUser() {
        User user = User.builder()
                .email("qkrdmswl1018@naver.com")
                .password("QWert123?")
                .name("name")
                .age(23)
                .hobby("hobby")
                .build();

        return userRepository.save(user);
    }

    private Post savedPost(User user) {
        Post post = Post.builder()
                .title(제목)
                .content(내용)
                .user(user)
                .build();
        return postRepository.save(post);
    }

}
