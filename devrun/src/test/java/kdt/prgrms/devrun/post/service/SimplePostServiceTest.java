package kdt.prgrms.devrun.post.service;

import kdt.prgrms.devrun.common.exception.PostNotFoundException;
import kdt.prgrms.devrun.domain.Post;
import kdt.prgrms.devrun.domain.User;
import kdt.prgrms.devrun.post.dto.AddPostRequestDto;
import kdt.prgrms.devrun.post.dto.DetailPostDto;
import kdt.prgrms.devrun.post.dto.EditPostRequestDto;
import kdt.prgrms.devrun.post.dto.SimplePostDto;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import java.util.ArrayList;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.MatcherAssert.*;

@SpringBootTest
@Transactional
class SimplePostServiceTest {

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private PostService postService;

    final Long INVALID_POST_ID = 1000000L;

    User user;
    User user2;
    Post post1;
    Post post2;

    @BeforeEach
    void setUp() {

        user = User.builder()
            .loginId("kjt3520")
            .loginPw("1234")
            .age(27)
            .name("김지훈")
            .email("devrunner21@gmail.com")
            .posts(new ArrayList<Post>())
            .build();
        entityManager.persist(user);

        user2 = User.builder()
            .loginId("kjt3520222")
            .loginPw("123422")
            .age(27)
            .name("김지훈22")
            .email("devrunner2222@gmail.com")
            .posts(new ArrayList<Post>())
            .build();
        entityManager.persist(user);
        entityManager.persist(user2);

        post1 = new Post("title1", "content1", user);
        post2 = new Post("title2", "content2", user2);

        entityManager.persist(post1);
        entityManager.persist(post2);

        entityManager.flush();
        entityManager.clear();

    }

    @Test
    @DisplayName("게시글 페이징 목록을 조회하여 DTO로 반환합니다.")
    void getAllPostPagingListTest() {

        // given
        final PageRequest pageRequest = PageRequest.of(0, 2);

        // when
        final Page<SimplePostDto> postDtoPage = postService.getPostPagingList(pageRequest);

        // then
        assertThat(postDtoPage.getTotalElements(), is(2L));
        assertThat(postDtoPage.getTotalPages(), is(1));
        assertThat(postDtoPage.getContent(), not(empty()));
    }

    @Test
    @DisplayName("PostId로 게시글의 상세정보를 조회하여 DTO로 반환합니다.")
    void getPostByIdTest() {

        final DetailPostDto foundPost = postService.getPostById(post1.getId());

        assertThat(foundPost, notNullValue());
        assertThat(foundPost.getId(), is(post1.getId()));
        assertThat(foundPost.getTitle(), is(post1.getTitle()));
        assertThat(foundPost.getContent(), is(post1.getContent()));

    }

    @Test
    @DisplayName("존재하지 않는 PostId로 게시글의 상세정보를 조회할경우 PostNotFoundException을 던집니다.")
    void getPostByIdTest_PostNotFoundException() {

        assertThatThrownBy(() -> postService.getPostById(INVALID_POST_ID)).isInstanceOf(PostNotFoundException.class);

    }

    @Test
    @DisplayName("새로운 게시글 등록하고 등록된 게시글 Id를 반환합니다.")
    void createPostTest() {

        String newPostTitle = "New Post Title";
        String newPostContent = "New Post Content";
        final AddPostRequestDto postForm = AddPostRequestDto.builder()
            .title(newPostTitle)
            .content(newPostContent)
            .createdBy(user.getLoginId())
            .build();

        final Long createdPostId = postService.createPost(postForm);

        final DetailPostDto foundPostDto = postService.getPostById(createdPostId);
        assertThat(foundPostDto, notNullValue());
        assertThat(foundPostDto.getTitle(), is(newPostTitle));
        assertThat(foundPostDto.getContent(), is(newPostContent));

    }

    @Test
    @DisplayName("Id에 해당하는 게시글을 수정하고 수정한 게시글 Id를 반환합니다.")
    void updatePostTest() {

        // given
        String updatePostTitle = "Update Post Title";
        String updatePostContent = "Update Post Content";

        final EditPostRequestDto postForm = EditPostRequestDto.builder()
            .title(updatePostTitle)
            .content(updatePostContent)
            .createdBy(user.getLoginId())
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
    @DisplayName("존재하지 않는 게시글Id에 해당하는 게시글을 수정할 경우 PostNotFoundException을 던집니다.")
    void updatePostTest_PostNotFoundException() {

        // given
        String updatePostTitle = "Update Post Title";
        String updatePostContent = "Update Post Content";

        final EditPostRequestDto postForm = EditPostRequestDto.builder()
            .title(updatePostTitle)
            .content(updatePostContent)
            .createdBy(user.getLoginId())
            .build();

        // when
        // then
        assertThatThrownBy(() -> postService.updatePost(INVALID_POST_ID, postForm)).isInstanceOf(PostNotFoundException.class);

    }

    @Test
    @DisplayName("Id에 해당하는 게시글을 삭제합니다.")
    void deletePostByIdTest() {

        postService.deletePostById(post1.getId());

        assertThatThrownBy(() -> postService.getPostById(post1.getId())).isInstanceOf(PostNotFoundException.class);

    }

    @Test
    @DisplayName("존재하지 않는 게시글 Id에 해당하는 게시글을 삭제할 경우, PostNotFoundException을 던집니다.")
    void deletePostByIdTest_PostNotFoundException을() {

        postService.deletePostById(post1.getId());

        assertThatThrownBy(() ->  postService.deletePostById(INVALID_POST_ID)).isInstanceOf(PostNotFoundException.class);

    }

}
