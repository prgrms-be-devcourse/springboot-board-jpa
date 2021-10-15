package kdt.prgrms.devrun.post.service;

import kdt.prgrms.devrun.common.dto.PageDto;
import kdt.prgrms.devrun.common.exception.PostNotFoundException;
import kdt.prgrms.devrun.domain.Post;
import kdt.prgrms.devrun.domain.User;
import kdt.prgrms.devrun.post.dto.AddPostRequestDto;
import kdt.prgrms.devrun.post.dto.DetailPostDto;
import kdt.prgrms.devrun.post.dto.EditPostRequestDto;
import kdt.prgrms.devrun.post.dto.SimplePostDto;
import kdt.prgrms.devrun.post.repository.PostRepository;
import kdt.prgrms.devrun.user.repository.UserRepository;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.MatcherAssert.*;

@SpringBootTest
@Transactional
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@DisplayName("SimplePostService 단위 테스트")
class SimplePostServiceTest {

    @Autowired
    private PostService postService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PostRepository postRepository;

    final Long INVALID_POST_ID = 1000000L;

    private User user1;
    private User user2;

    @BeforeAll
    void setUp() {

        user1 = User.builder()
            .loginId("kjt3520")
            .loginPw("1234")
            .age(27)
            .name("김지훈")
            .email("devrunner21@gmail.com")
            .posts(new ArrayList<Post>())
            .build();

        user2 = User.builder()
            .loginId("kjt3520222")
            .loginPw("123422")
            .age(27)
            .name("이수민")
            .email("devrunner2222@gmail.com")
            .posts(new ArrayList<Post>())
            .build();

        userRepository.save(user1);
        userRepository.save(user2);

    }

    @Test
    @DisplayName("getAllPostPagingList()는 페이지 정보를 파라미터로 받으면, 게시글 페이징 목록을 조회하여 반환합니다.")
    void test_getAllPostPagingList() {

        // given
        final Post post1 = Post.builder()
            .title("제목 1")
            .content("내용 1")
            .user(user1)
            .build();
        final Post post2 = Post.builder()
            .title("제목 2")
            .content("내용 2")
            .user(user2)
            .build();
        postRepository.save(post1);
        postRepository.save(post2);

        final PageRequest pageRequest = PageRequest.of(0, 2);

        // when
        final PageDto<SimplePostDto> postDtoPage = postService.getPostPagingList(pageRequest);

        // then
        assertThat(postDtoPage.getTotalCount(), is(2L));
        assertThat(postDtoPage.getPageNo(), is(0));
        assertThat(postDtoPage.getPageSize(), is(2));
        assertThat(postDtoPage.getList(), not(empty()));
    }

    @Test
    @DisplayName("getPostById()는 올바른 PostId를 파라미터로 받으면, 게시글의 상세정보를 조회하여 반환합니다.")
    void test_getPostById_with_validId() {

        // given
        final Post post1 = Post.builder()
            .title("제목 1")
            .content("내용 1")
            .user(user1)
            .build();
        postRepository.save(post1);
        postRepository.flush();

        final DetailPostDto foundPost = postService.getPostById(post1.getId());

        assertThat(foundPost, notNullValue());
        assertThat(foundPost.getId(), is(post1.getId()));
        assertThat(foundPost.getTitle(), is(post1.getTitle()));
        assertThat(foundPost.getContent(), is(post1.getContent()));

    }

    @Test
    @DisplayName("getPostById()는 존재하지 않는 Id를 파라미터로 받으면, PostNotFoundException을 던집니다.")
    void test_getPostById_with_invalidId() {

        assertThatThrownBy(() -> postService.getPostById(INVALID_POST_ID)).isInstanceOf(PostNotFoundException.class);

    }

    @Test
    @DisplayName("createPost()는 생성할 게시글 정보를 파라미터로 받으면, 새로운 게시글 등록하고 등록된 게시글 Id를 반환합니다.")
    void test_createPost() {

        // given
        String newPostTitle = "New Post Title";
        String newPostContent = "New Post Content";
        final AddPostRequestDto postForm = AddPostRequestDto.builder()
            .title(newPostTitle)
            .content(newPostContent)
            .createdBy(user1.getLoginId())
            .build();

        // when
        final Long createdPostId = postService.createPost(postForm);

        // then
        final DetailPostDto foundPostDto = postService.getPostById(createdPostId);
        assertThat(foundPostDto, notNullValue());
        assertThat(foundPostDto.getTitle(), is(newPostTitle));
        assertThat(foundPostDto.getContent(), is(newPostContent));

    }

    @Test
    @DisplayName("updatePost()는 목록에 존재하는 Id와 수정할 정보를 파라미터로 받는다면, 해당하는 게시글을 수정하고 수정한 게시글 Id를 반환합니다.")
    void test_updatePost_with_validId() {

        // given
        final Post post1 = Post.builder()
            .title("제목 1")
            .content("내용 1")
            .user(user1)
            .build();
        postRepository.save(post1);
        postRepository.flush();

        String updatePostTitle = "Update Post Title";
        String updatePostContent = "Update Post Content";
        final EditPostRequestDto postForm = EditPostRequestDto.builder()
            .title(updatePostTitle)
            .content(updatePostContent)
            .createdBy(user1.getLoginId())
            .build();

        // when
        final Long updatedPostId = postService.updatePost(post1.getId(), postForm);

        // then
        final DetailPostDto updatedPostDto = postService.getPostById(updatedPostId);
        assertThat(updatedPostDto, notNullValue());
        assertThat(updatedPostDto.getTitle(), is(updatePostTitle));
        assertThat(updatedPostDto.getContent(), is(updatePostContent));

    }

    @Test
    @DisplayName("updatePost()는 목록에 존재하지 않는 Id와 수정할 정보를 파라미터로 받으면, PostNotFoundException을 던집니다.")
    void test_updatePostTest_invalidId() {

        // given
        String updatePostTitle = "Update Post Title";
        String updatePostContent = "Update Post Content";

        final EditPostRequestDto postForm = EditPostRequestDto.builder()
            .title(updatePostTitle)
            .content(updatePostContent)
            .createdBy(user1.getLoginId())
            .build();

        // when
        // then
        assertThatThrownBy(() -> postService.updatePost(INVALID_POST_ID, postForm)).isInstanceOf(PostNotFoundException.class);

    }

    @Test
    @DisplayName("deletePostById()는 목록에 존재하는 Id를 파라미터로 받으면, id에 해당하는 게시글을 삭제합니다.")
    void test_deletePostById() {

        // given
        final Post post1 = Post.builder()
            .title("제목 1")
            .content("내용 1")
            .user(user1)
            .build();
        postRepository.save(post1);
        postRepository.flush();

        // when
        postService.deletePostById(post1.getId());

        // then
        assertThatThrownBy(() -> postService.getPostById(post1.getId())).isInstanceOf(PostNotFoundException.class);

    }

    @Test
    @DisplayName("deletePostById()는 존재하지 않는 Id를 파라미터로 받으면, PostNotFoundException을 던집니다.")
    void deletePostByIdTest_PostNotFoundException을() {

        assertThatThrownBy(() ->  postService.deletePostById(INVALID_POST_ID)).isInstanceOf(PostNotFoundException.class);

    }

}
