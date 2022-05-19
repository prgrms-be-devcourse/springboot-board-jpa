package com.prgrms.hyuk.service;

import static com.prgrms.hyuk.exception.ExceptionMessage.INVALID_POST_ID_EXP_MSG;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

import com.prgrms.hyuk.domain.post.Content;
import com.prgrms.hyuk.domain.post.Post;
import com.prgrms.hyuk.domain.post.Title;
import com.prgrms.hyuk.domain.user.Age;
import com.prgrms.hyuk.domain.user.Hobby;
import com.prgrms.hyuk.domain.user.Name;
import com.prgrms.hyuk.domain.user.User;
import com.prgrms.hyuk.dto.PostCreateRequest;
import com.prgrms.hyuk.dto.PostUpdateRequest;
import com.prgrms.hyuk.dto.UserDto;
import com.prgrms.hyuk.exception.InvalidPostIdException;
import com.prgrms.hyuk.repository.PostRepository;
import com.prgrms.hyuk.service.converter.Converter;
import java.util.List;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
@TestInstance(Lifecycle.PER_CLASS)
class PostServiceIntegrationTest {

    @Autowired
    private PostRepository postRepository;

    private PostService postService;

    private User user;

    @BeforeAll
    void setUpBeforeTests() {
        postService = new PostService(postRepository, new Converter());
    }

    @BeforeEach
    void setUp() {
        user = new User(new Name("pang"), new Age(26), Hobby.SOCCER);
    }

    @Test
    @DisplayName("게시글 작성")
    void testCreateSuccessWhenNewUserCreatePost() {
        //given
        var postCreateRequest = new PostCreateRequest(
            "this is title...",
            "content",
            new UserDto(
                "eunhyuk",
                26,
                "soccer"
            )
        );

        //when
        var savedId = postService.create(postCreateRequest);

        //then
        var post = postRepository.findById(savedId).get();
        assertAll(
            () -> assertThat(post).isNotNull(),
            () -> assertThat(post.getTitle().getTitle()).isEqualTo(postCreateRequest.getTitle()),

            () -> assertThat(post.getUser()).isNotNull(),
            () -> assertThat(post.getUser().getName().getName())
                .isEqualTo(postCreateRequest.getUserDto().getName())
        );
    }

    @Test
    @DisplayName("게시글 조회 (페이징)")
    void testFindPosts() {
        //given
        var post1 = generatePostAssignedByUser(user);
        var post2 = generatePostAssignedByUser(user);

        postRepository.saveAll(List.of(post1, post2));

        //when
        var posts = postService.findPosts(0, 5);

        //then
        assertThat(posts).hasSize(2);
    }

    @Test
    @DisplayName("게시글 단건 조회 - 게시글 존재")
    void testFindByIdWhenExist() {
        //given
        var post = generatePostAssignedByUser(user);

        var savedPost = postRepository.save(post);

        //when
        var retrievedPost = postService.findPost(savedPost.getId());

        //then
        assertThat(retrievedPost.getId()).isEqualTo(savedPost.getId());
    }

    @Test
    @DisplayName("게시글 단건 조회 - 게시글 없음")
    void testFindByIdFailBecauseInvalidPostId() {
        //given
        Long invalidId = 1L;

        //when
        //then
        assertThatThrownBy(() -> postService.findPost(invalidId))
            .isInstanceOf(InvalidPostIdException.class)
            .hasMessageContaining(INVALID_POST_ID_EXP_MSG.getMessage());
    }

    @Test
    @DisplayName("게시글 수정 성공")
    void testUpdatePostSuccess() {
        //given
        var post = generatePostAssignedByUser(user);
        var savedPost = postRepository.save(post);

        var postUpdateRequest = new PostUpdateRequest(
            "this is updated title...",
            "this is updated content...");

        //when
        postService.updatePost(savedPost.getId(), postUpdateRequest);

        //then
        var updatedPost = postRepository.findById(savedPost.getId()).get();
        assertAll(
            () -> assertThat(updatedPost).isNotNull(),
            () -> assertThat(updatedPost.getId()).isEqualTo(savedPost.getId()),
            () -> assertThat(updatedPost.getTitle().getTitle())
                .isEqualTo(postUpdateRequest.getTitle()),
            () -> assertThat(updatedPost.getContent()
                .getContent()).isEqualTo(postUpdateRequest.getContent())
        );
    }

    @Test
    @DisplayName("게시글 수정 실패 - 존재하지 않는 게시글")
    void testUpdatePostFailBecauseInvalidPostId() {
        //given
        Long invalidId = 1L;
        var postUpdateRequest = new PostUpdateRequest(
            "this is title...",
            "this is content...");

        //when
        //then
        assertThatThrownBy(() -> postService.updatePost(invalidId, postUpdateRequest))
            .isInstanceOf(InvalidPostIdException.class)
            .hasMessageContaining(INVALID_POST_ID_EXP_MSG.getMessage());
    }

    private Post generatePostAssignedByUser(User user) {
        var post = new Post(
            new Title("this is title..."),
            new Content("this is content...")
        );
        post.assignUser(user);
        return post;
    }
}
